package com.heartape.hap.statistics;

import com.heartape.hap.constant.ResourceStatisticsKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class TopicFollowStatistics extends AbstractPairOperateStatistics {

    @Override
    public String targetKey(long targetId) {
        return ResourceStatisticsKeyConstant.FOLLOW_TOPIC + targetId;
    }

    @Override
    public String sponsorKey(long sponsorId) {
        return ResourceStatisticsKeyConstant.FOLLOW_TOPIC + ResourceStatisticsKeyConstant.SPONSOR + sponsorId;
    }
}
