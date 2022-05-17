package com.heartape.hap.business.statistics;

import com.heartape.hap.business.constant.ResourceRedisKeyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
public class DiscussCollectionStatistics extends AbstractPairOperateStatistics {

    @Autowired
    private RedisTemplate<String,Long> longRedisTemplate;

    public ZSetOperations<String, Long> getZSetOperations() {
        return longRedisTemplate.opsForZSet();
    }

    @Override
    public String getKeyHeader(long sourceId) {
        return ResourceRedisKeyConstant.COLLECTION_ARTICLE + sourceId;
    }

    @Override
    public String getSponsorKeyHeader(long sponsorId) {
        return ResourceRedisKeyConstant.COLLECTION_ARTICLE + ResourceRedisKeyConstant.SPONSOR + sponsorId;
    }
}
