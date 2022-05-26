package com.heartape.hap;

import com.heartape.hap.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class ArticleHotStatistics extends AbstractTopCumulativeOperateStatistics {

    @Override
    public String getKey() {
        return ResourceRedisKeyConstant.HEAT_ARTICLE;
    }
}
