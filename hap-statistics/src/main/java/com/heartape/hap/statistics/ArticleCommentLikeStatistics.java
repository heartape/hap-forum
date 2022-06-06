package com.heartape.hap.statistics;

import com.heartape.hap.constant.ResourceStatisticsKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class ArticleCommentLikeStatistics extends AbstractTypeOperateStatistics {

    @Override
    public String positiveKey(long sourceId) {
        return ResourceStatisticsKeyConstant.LIKE_ARTICLE_COMMENT + ResourceStatisticsKeyConstant.POSITIVE + sourceId;
    }

    @Override
    public String negativeKey(long sourceId) {
        return ResourceStatisticsKeyConstant.LIKE_ARTICLE_COMMENT + ResourceStatisticsKeyConstant.NEGATIVE + sourceId;
    }

    @Override
    public String sponsorKey(long sponsorId) {
        return ResourceStatisticsKeyConstant.LIKE_ARTICLE_COMMENT + ResourceStatisticsKeyConstant.SPONSOR + sponsorId;
    }
}
