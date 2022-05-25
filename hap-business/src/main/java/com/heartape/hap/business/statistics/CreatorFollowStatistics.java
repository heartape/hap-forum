package com.heartape.hap.business.statistics;

import com.heartape.hap.business.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class CreatorFollowStatistics extends AbstractPairOperateStatistics {

    @Override
    public String targetKey(long targetId) {
        return ResourceRedisKeyConstant.FOLLOW_CREATOR + targetId;
    }

    @Override
    public String sponsorKey(long sponsorId) {
        return ResourceRedisKeyConstant.FOLLOW_CREATOR + ResourceRedisKeyConstant.SPONSOR + sponsorId;
    }
}
