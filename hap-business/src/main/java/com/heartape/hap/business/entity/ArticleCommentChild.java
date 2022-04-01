package com.heartape.hap.business.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class ArticleCommentChild extends BaseEntity {

    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    @TableField(value = "article_id", updateStrategy = FieldStrategy.NEVER)
    private Long articleId;

    @TableField(value = "parent_id", updateStrategy = FieldStrategy.NEVER)
    private Long parentId;

    @TableField(fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private Long uid;

    @TableField(fill = FieldFill.INSERT)
    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    private String nickname;

    @TableField(value = "child_to_child", updateStrategy = FieldStrategy.NEVER)
    private Boolean childToChild;

    @TableField(value = "child_target", updateStrategy = FieldStrategy.NEVER)
    private Long childTarget;

    @TableField(value = "child_target_name", fill = FieldFill.INSERT)
    private String childTargetName;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String content;

}
