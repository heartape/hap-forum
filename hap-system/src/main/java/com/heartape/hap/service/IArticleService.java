package com.heartape.hap.service;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.entity.bo.ArticleSimpleBO;
import com.heartape.hap.entity.bo.ArticleBO;
import com.heartape.hap.entity.dto.ArticleDTO;
import com.heartape.hap.statistics.AbstractTypeOperateStatistics;

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
     * 文章热度变化
     */
    void heatChange(Long articleId, Integer delta);

    /**
     * 点赞文章
     */
    AbstractTypeOperateStatistics.TypeNumber like(Long articleId);

    /**
     * 踩文章
     */
    AbstractTypeOperateStatistics.TypeNumber dislike(Long articleId);

    /**
     * 删除文章
     */
    void remove(Long articleId);

}
