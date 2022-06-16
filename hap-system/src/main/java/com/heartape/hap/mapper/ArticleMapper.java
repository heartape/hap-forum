package com.heartape.hap.mapper;

import com.heartape.hap.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询带标签的文章详情
     */
    Article selectOneLabel(@Param("article_id") Long articleId);
}
