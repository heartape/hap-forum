package com.heartape.hap.statistics;

import com.heartape.hap.constant.ResourceStatisticsKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class CreatorFollowStatistics extends AbstractPairOperateStatistics {

    @Override
    public String targetKey(long targetId) {
        return ResourceStatisticsKeyConstant.FOLLOW_CREATOR + targetId;
    }

    @Override
    public String sponsorKey(long sponsorId) {
        return ResourceStatisticsKeyConstant.FOLLOW_CREATOR + ResourceStatisticsKeyConstant.SPONSOR + sponsorId;
    }
}
