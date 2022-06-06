package com.heartape.hap.statistics;

import com.heartape.hap.constant.ResourceStatisticsKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class DiscussCommentChildHotStatistics extends AbstractCumulativeOperateStatistics {

    @Override
    public String getKey(long mainId) {
        return ResourceStatisticsKeyConstant.HEAT_DISCUSS_COMMENT_CHILD + mainId;
    }
}
