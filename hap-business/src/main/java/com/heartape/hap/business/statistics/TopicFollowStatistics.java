package com.heartape.hap.business.statistics;

import com.heartape.hap.business.constant.ResourceRedisKeyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
public class TopicFollowStatistics extends AbstractPairOperateStatistics {

    @Autowired
    private RedisTemplate<String,Long> longRedisTemplate;

    @Override
    public ZSetOperations<String, Long> getZSetOperations() {
        return longRedisTemplate.opsForZSet();
    }

    @Override
    public String getKeyHeader(long sourceId) {
        return ResourceRedisKeyConstant.FOLLOW_TOPIC + sourceId;
    }

    @Override
    public String getSponsorKeyHeader(long sponsorId) {
        return ResourceRedisKeyConstant.FOLLOW_TOPIC + ResourceRedisKeyConstant.SPONSOR + sponsorId;
    }
}
