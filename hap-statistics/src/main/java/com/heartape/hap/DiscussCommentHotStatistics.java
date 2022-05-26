package com.heartape.hap;

import com.heartape.hap.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class DiscussCommentHotStatistics extends AbstractCumulativeOperateStatistics {

    public final static int INIT_HOT = 100;

    @Override
    public String getKey(long mainId) {
        return ResourceRedisKeyConstant.HEAT_DISCUSS_COMMENT + mainId;
    }
}
