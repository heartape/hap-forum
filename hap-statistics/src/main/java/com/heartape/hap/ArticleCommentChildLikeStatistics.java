package com.heartape.hap;

import com.heartape.hap.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class ArticleCommentChildLikeStatistics extends AbstractTypeOperateStatistics {

    @Override
    public String positiveKey(long sourceId) {
        return ResourceRedisKeyConstant.LIKE_ARTICLE_COMMENT_CHILD + ResourceRedisKeyConstant.POSITIVE + sourceId;
    }

    @Override
    public String negativeKey(long sourceId) {
        return ResourceRedisKeyConstant.LIKE_ARTICLE_COMMENT_CHILD + ResourceRedisKeyConstant.NEGATIVE + sourceId;
    }

    @Override
    public String sponsorKey(long sponsorId) {
        return ResourceRedisKeyConstant.LIKE_ARTICLE_COMMENT_CHILD + ResourceRedisKeyConstant.SPONSOR + sponsorId;
    }
}
