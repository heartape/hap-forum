package com.heartape.hap.business.statistics;

import com.heartape.hap.business.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class ArticleLikeStatistics extends SourceOperateStatistics {

    private final String keyHeader = ResourceRedisKeyConstant.LIKE_ARTICLE;
}
