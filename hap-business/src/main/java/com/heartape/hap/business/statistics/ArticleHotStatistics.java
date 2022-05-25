package com.heartape.hap.business.statistics;

import com.heartape.hap.business.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class ArticleHotStatistics extends AbstractTopCumulativeOperateStatistics {

    @Override
    public String getKey() {
        return ResourceRedisKeyConstant.HEAT_ARTICLE;
    }
}
