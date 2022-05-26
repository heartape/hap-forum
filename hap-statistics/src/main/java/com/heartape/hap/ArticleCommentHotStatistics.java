package com.heartape.hap;

import com.heartape.hap.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class ArticleCommentHotStatistics extends AbstractCumulativeOperateStatistics {

    @Override
    public String getKey(long mainId) {
        return ResourceRedisKeyConstant.HEAT_ARTICLE_COMMENT;
    }
}
