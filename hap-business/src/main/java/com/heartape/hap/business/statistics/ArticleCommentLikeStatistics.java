package com.heartape.hap.business.statistics;

import com.heartape.hap.business.constant.ResourceRedisKeyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
public class ArticleCommentLikeStatistics extends AbstractTypeOperateStatistics {

    @Autowired
    private RedisTemplate<String,Long> longRedisTemplate;

    @Override
    public ZSetOperations<String, Long> getZSetOperations() {
        return longRedisTemplate.opsForZSet();
    }

    @Override
    public String getKeyHeader(long sourceId) {
        return ResourceRedisKeyConstant.LIKE_ARTICLE_COMMENT + sourceId;
    }

    @Override
    public String getUserKeyHeader(long uid) {
        return ResourceRedisKeyConstant.LIKE_ARTICLE_COMMENT + "uid:" + uid;
    }
}
