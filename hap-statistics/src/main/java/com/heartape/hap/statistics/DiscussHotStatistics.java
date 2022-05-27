package com.heartape.hap.statistics;

import com.heartape.hap.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class DiscussHotStatistics extends AbstractCumulativeOperateStatistics {

    @Override
    public String getKey(long mainId) {
        return ResourceRedisKeyConstant.HEAT_DISCUSS + mainId;
    }
}
