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
public class DiscussCommentChild extends BaseEntity {

    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    @TableField(value = "topic_id", updateStrategy = FieldStrategy.NEVER)
    private Long topicId;

    @TableField(value = "discuss_id", updateStrategy = FieldStrategy.NEVER)
    private Long discussId;

    @TableField(value = "parent_id", updateStrategy = FieldStrategy.NEVER)
    private Long parentId;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Long uid;

    private String avatar;

    private String nickname;

    @TableField(value = "child_to_child", updateStrategy = FieldStrategy.NEVER)
    private Boolean childToChild;

    @TableField(value = "child_target", updateStrategy = FieldStrategy.NEVER)
    private Long childTarget;

    @TableField(value = "child_target_name")
    private String childTargetName;

    private String content;

}
