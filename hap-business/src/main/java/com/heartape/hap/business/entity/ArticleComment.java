package com.heartape.hap.business.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleComment extends BaseEntity {

    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    @TableField(value = "article_id", updateStrategy = FieldStrategy.NEVER)
    private Long articleId;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Long uid;

    private String avatar;

    private String nickname;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String content;

    @ApiModelProperty("置顶条件，根据不同的搜索条件判断是否置顶（暂不实现）")
    @TableField(typeHandler = JacksonTypeHandler.class, insertStrategy = FieldStrategy.NEVER)
    private Topping topping;

    /**
     * 用于返回子评论数量
     */
    @TableField(exist = false)
    private Integer total;
}
