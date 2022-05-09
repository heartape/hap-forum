package com.heartape.hap.business.mapper;

import com.heartape.hap.business.entity.ArticleComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
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
@Mapper
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {

    /**
     * 查询父评论列表并返回子评论总数
     */
    List<ArticleComment> selectWithChildCount(@Param("article_id") Long articleId);
}
