package com.heartape.hap.service;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.entity.ArticleCommentChild;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.entity.bo.ArticleCommentChildBO;
import com.heartape.hap.entity.dto.ArticleCommentChildDTO;
import com.heartape.hap.statistics.AbstractTypeOperateStatistics;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
public interface IArticleCommentChildService extends IService<ArticleCommentChild> {

    /**
     * 发布子评论
     */
    void create(ArticleCommentChildDTO articleCommentChildDTO);

    /**
     * 发布子评论的评论
     */
    void createToChild(ArticleCommentChildDTO articleCommentChildDTO);

    /**
     * 查询子评论列表
     */
    PageInfo<ArticleCommentChildBO> list(Long commentId, Integer page, Integer size);

    /**
     * 点赞
     */
    AbstractTypeOperateStatistics.TypeNumber like(Long commentId);

    /**
     * 点踩
     */
    AbstractTypeOperateStatistics.TypeNumber dislike(Long commentId);

    /**
     * 删除子评论
     */
    void remove(Long commentId);
}
