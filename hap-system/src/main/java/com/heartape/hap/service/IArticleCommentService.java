package com.heartape.hap.service;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.entity.ArticleComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.entity.bo.ArticleCommentBO;
import com.heartape.hap.entity.dto.ArticleCommentDTO;
import com.heartape.hap.statistics.AbstractTypeOperateStatistics;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
public interface IArticleCommentService extends IService<ArticleComment> {

    /**
     * 发布父评论
     */
    void create(ArticleCommentDTO articleCommentDTO);

    /**
     * 父评论列表查询
     */
    PageInfo<ArticleCommentBO> list(Long articleId, Integer page, Integer size);

    /**
     * 点赞
     */
    AbstractTypeOperateStatistics.TypeNumber like(Long commentId);

    /**
     * 点踩
     */
    AbstractTypeOperateStatistics.TypeNumber dislike(Long commentId);

    /**
     * 删除父评论
     */
    void removeOne(Long commentId);

}
