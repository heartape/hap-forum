package com.heartape.hap.business.service;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.business.entity.bo.ArticleSimpleBO;
import com.heartape.hap.business.entity.bo.ArticleBO;
import com.heartape.hap.business.entity.dto.ArticleDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
public interface IArticleService extends IService<Article> {

    /**
     * 发布文章
     */
    void create(ArticleDTO articleDTO);

    /**
     * 文章列表
     */
    PageInfo<ArticleSimpleBO> list(Integer page, Integer size);

    /**
     * 文章详情
     */
    ArticleBO detail(Long articleId);

    /**
     * 点赞文章
     */
    void like(Long articleId);

    /**
     * 踩文章
     */
    void dislike(Long articleId);

    /**
     * 删除文章
     */
    void remove(Long articleId);

}
