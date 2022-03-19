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

    @TableField(fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private Long uid;

    @TableField(fill = FieldFill.INSERT)
    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    private String nickname;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String content;

    @TableField(typeHandler = JacksonTypeHandler.class, fill = FieldFill.INSERT)
    private List<Long> childrenId;

    @TableField(exist = false)
    private List<ArticleCommentChild> children;

    @ApiModelProperty("置顶条件，根据不同的搜索条件判断是否置顶（暂不实现）")
    @TableField(typeHandler = JacksonTypeHandler.class, insertStrategy = FieldStrategy.NEVER)
    private Topping topping;

}
