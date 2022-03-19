package com.heartape.hap.business.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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

    @TableId(value = "comment_id", type = IdType.ASSIGN_ID)
    private Long commentId;

    @TableField("topic_id")
    private Long topicId;

    @TableField("discuss_id")
    private Long discussId;

    @TableField("parent_id")
    private Long parentId;

    @TableField(fill = FieldFill.INSERT)
    private Long uid;

    private String avatar;

    private String nickname;

    @TableField("child_to_child")
    private Boolean childToChild;

    @TableField("child_target")
    private Long childTarget;

    private String content;

}
