package com.heartape.hap.business.statistics;

import com.heartape.hap.business.constant.ResourceRedisKeyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
public class ArticleCommentHotStatistics extends AbstractCumulativeOperateStatistics {

    @Autowired
    private RedisTemplate<String,Long> longRedisTemplate;

    @Override
    public ZSetOperations<String, Long> getZSetOperations() {
        return longRedisTemplate.opsForZSet();
    }

    @Override
    public String getKey(Long mainId) {
        return ResourceRedisKeyConstant.HOT_ARTICLE_COMMENT;
    }
}
