package com.heartape.hap.statistics;

import com.heartape.hap.constant.ResourceStatisticsKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class ArticleCommentChildLikeStatistics extends AbstractTypeOperateStatistics {

    @Override
    public String positiveKey(long sourceId) {
        return ResourceStatisticsKeyConstant.LIKE_ARTICLE_COMMENT_CHILD + ResourceStatisticsKeyConstant.POSITIVE + sourceId;
    }

    @Override
    public String negativeKey(long sourceId) {
        return ResourceStatisticsKeyConstant.LIKE_ARTICLE_COMMENT_CHILD + ResourceStatisticsKeyConstant.NEGATIVE + sourceId;
    }

    @Override
    public String sponsorKey(long sponsorId) {
        return ResourceStatisticsKeyConstant.LIKE_ARTICLE_COMMENT_CHILD + ResourceStatisticsKeyConstant.SPONSOR + sponsorId;
    }
}
