package com.heartape.hap;

import com.heartape.hap.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class ArticleCommentLikeStatistics extends AbstractTypeOperateStatistics {

    @Override
    public String positiveKey(long sourceId) {
        return ResourceRedisKeyConstant.LIKE_ARTICLE_COMMENT + ResourceRedisKeyConstant.POSITIVE + sourceId;
    }

    @Override
    public String negativeKey(long sourceId) {
        return ResourceRedisKeyConstant.LIKE_ARTICLE_COMMENT + ResourceRedisKeyConstant.NEGATIVE + sourceId;
    }

    @Override
    public String sponsorKey(long sponsorId) {
        return ResourceRedisKeyConstant.LIKE_ARTICLE_COMMENT + ResourceRedisKeyConstant.SPONSOR + sponsorId;
    }
}
