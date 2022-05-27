package com.heartape.hap.statistics;

import com.heartape.hap.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class DiscussLikeStatistics extends AbstractTypeOperateStatistics {

    @Override
    public String positiveKey(long sourceId) {
        return ResourceRedisKeyConstant.LIKE_DISCUSS + ResourceRedisKeyConstant.POSITIVE + sourceId;
    }

    @Override
    public String negativeKey(long sourceId) {
        return ResourceRedisKeyConstant.LIKE_DISCUSS + ResourceRedisKeyConstant.NEGATIVE + sourceId;
    }

    @Override
    public String sponsorKey(long sponsorId) {
        return ResourceRedisKeyConstant.LIKE_DISCUSS + ResourceRedisKeyConstant.SPONSOR + sponsorId;
    }
}
