package com.heartape.hap.statistics;

import com.heartape.hap.constant.ResourceStatisticsKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class ArticleLikeStatistics extends AbstractTypeOperateStatistics {

    @Override
    public String positiveKey(long targetId) {
        return ResourceStatisticsKeyConstant.LIKE_ARTICLE + ResourceStatisticsKeyConstant.POSITIVE + targetId;
    }

    @Override
    public String negativeKey(long targetId) {
        return ResourceStatisticsKeyConstant.LIKE_ARTICLE + ResourceStatisticsKeyConstant.NEGATIVE + targetId;
    }

    @Override
    public String sponsorKey(long sponsorId) {
        return ResourceStatisticsKeyConstant.LIKE_ARTICLE + ResourceStatisticsKeyConstant.SPONSOR + sponsorId;
    }
}
