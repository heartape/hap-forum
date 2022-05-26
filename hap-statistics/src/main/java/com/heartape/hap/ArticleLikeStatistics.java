package com.heartape.hap;

import com.heartape.hap.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class ArticleLikeStatistics extends AbstractTypeOperateStatistics {

    @Override
    public String positiveKey(long targetId) {
        return ResourceRedisKeyConstant.LIKE_ARTICLE + ResourceRedisKeyConstant.POSITIVE + targetId;
    }

    @Override
    public String negativeKey(long targetId) {
        return ResourceRedisKeyConstant.LIKE_ARTICLE + ResourceRedisKeyConstant.NEGATIVE + targetId;
    }

    @Override
    public String sponsorKey(long sponsorId) {
        return ResourceRedisKeyConstant.LIKE_ARTICLE + ResourceRedisKeyConstant.SPONSOR + sponsorId;
    }
}
