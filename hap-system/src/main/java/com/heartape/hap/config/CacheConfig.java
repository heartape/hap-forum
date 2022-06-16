package com.heartape.hap.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Cacheable: 配置在方法上表示其返回值将被加入缓存。同时在查询时，会先从缓存中获取，若不存在才再发起对数据库的访问
 * @CachePut: 配置于方法上时，能够根据参数定义条件来进行缓存，其与 @Cacheable不同的是使用 @CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中，所以主要用于数据新增和修改操作上
 * @CacheEvict: 配置于方法上时，表示从缓存中移除相应数据。
 */
@ConfigurationProperties(prefix = "cache")
@Configuration
@Slf4j
@Data
@EnableCaching
public class CacheConfig {
    @Data
    public static class LocalCache {
        private Integer timeout;
        private Integer max = 500;
    }
    //该变量名称会与配置文件中相对应
    private Map<String,LocalCache> localCache;
    private Map<String,Integer> remoteCache;

    @Primary
    @Bean
    public CacheManager caffeineCacheManager(Ticker ticker) {
        SimpleCacheManager manager = new SimpleCacheManager();
        if (localCache != null) {
            List<CaffeineCache> caches = localCache.entrySet().stream()
                    .map(entry -> buildCache(entry.getKey(), entry.getValue(), ticker))
                    .collect(Collectors.toList());
            manager.setCaches(caches);
        }
        return manager;
    }

    private CaffeineCache buildCache(String name, LocalCache cacheSpec, Ticker ticker) {
        log.info("Cache {} specified timeout of {} min, max of {}", name, cacheSpec.getTimeout(), cacheSpec.getMax());
        final Caffeine<Object, Object> caffeineBuilder = Caffeine
                .newBuilder()
                .expireAfterWrite(cacheSpec.getTimeout(), TimeUnit.SECONDS)
                .maximumSize(cacheSpec.getMax())
                .ticker(ticker);
        return new CaffeineCache(name, caffeineBuilder.build());
    }

    @Bean
    public CacheManager redisPageCacheManager(RedisConnectionFactory redisConnectionFactory) {
        return redisCacheManager(redisConnectionFactory, PageInfo.class);
    }

    private CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory, Class<?> clazz) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .prefixCacheNameWith("cache:")//缓存前缀
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(clazz)));

        //不同缓存，不同过期时间
        Map<String, RedisCacheConfiguration> redisCacheConfigMap = new HashMap<>(remoteCache.size());
        for (Map.Entry<String, Integer> entry : remoteCache.entrySet()) {
            redisCacheConfigMap.put(entry.getKey(), redisCacheConfiguration.entryTtl(Duration.ofSeconds(entry.getValue())));
        }

        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration)
                .initialCacheNames(remoteCache.keySet())//这里很重要
                .withInitialCacheConfigurations(redisCacheConfigMap)  //这里很重要
                .build();
    }

    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }
}
