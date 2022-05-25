package com.heartape.hap.business.statistics;

import com.heartape.hap.business.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class ArticleCommentChildHotStatistics extends AbstractCumulativeOperateStatistics {

    public final static int INIT_HOT = 100;

    @Override
    public String getKey(long mainId) {
        return ResourceRedisKeyConstant.HEAT_ARTICLE_COMMENT_CHILD + mainId;
    }
}
