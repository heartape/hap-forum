package com.heartape.hap.business.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.heartape.hap.business.entity.ArticleComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {

    /**
     * 查询带子评论的评论树
     */
    List<ArticleComment> selectTreeList(@Param("article_id") Long articleId, @Param("page") Integer page, @Param("size") Integer size);
}
