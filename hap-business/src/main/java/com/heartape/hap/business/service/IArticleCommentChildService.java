package com.heartape.hap.business.service;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.ArticleCommentChild;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.business.entity.bo.ArticleCommentChildBO;
import com.heartape.hap.business.entity.dto.ArticleCommentChildDTO;

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
    boolean like(Long commentId);

    /**
     * 点踩
     */
    boolean dislike(Long commentId);

    /**
     * 删除子评论
     */
    void remove(Long commentId);
}
