package com.heartape.hap.business.statistics;

import com.heartape.hap.business.constant.ResourceRedisKeyConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

@Component
public class ArticleCommentHotStatistics extends AbstractCumulativeOperateStatistics {

    public final static int INIT_HOT = 100;

    @Override
    public String getKey(long mainId) {
        return ResourceRedisKeyConstant.HEAT_ARTICLE_COMMENT;
    }
}
