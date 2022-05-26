package com.heartape.hap.statistics;

import com.heartape.hap.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class TopicFollowStatistics extends AbstractPairOperateStatistics {

    @Override
    public String targetKey(long targetId) {
        return ResourceRedisKeyConstant.FOLLOW_TOPIC + targetId;
    }

    @Override
    public String sponsorKey(long sponsorId) {
        return ResourceRedisKeyConstant.FOLLOW_TOPIC + ResourceRedisKeyConstant.SPONSOR + sponsorId;
    }
}