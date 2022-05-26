package com.heartape.hap.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.heartape.hap.entity.HapUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Long> longRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        Jackson2JsonRedisSerializer<Long> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Long.class);
        return process(factory,template,jackson2JsonRedisSerializer);
    }

    @Bean
    public RedisTemplate<String,Integer> intRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Integer> template = new RedisTemplate<>();
        Jackson2JsonRedisSerializer<Integer> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Integer.class);
        return process(factory,template,jackson2JsonRedisSerializer);
    }

    @Bean
    public RedisTemplate<String,Object> objectRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        return process(factory,template,jackson2JsonRedisSerializer);
    }

    @Bean
    public RedisTemplate<String, HapUserDetails> userDetailsRedisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, HapUserDetails> template = new RedisTemplate<>();
        Jackson2JsonRedisSerializer<HapUserDetails> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(HapUserDetails.class);
        return process(factory,template,jackson2JsonRedisSerializer);
    }

    private <T> RedisTemplate<String,T> process(RedisConnectionFactory factory, RedisTemplate<String, T> template, Jackson2JsonRedisSerializer<T> serializer) {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(om);

        template.setConnectionFactory(factory);
        //key 采用的String 的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        //value 序列化方式采用jackson
        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }

}
