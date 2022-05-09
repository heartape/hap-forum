package com.heartape.hap.business.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * main:主体，如文章
 * target：目标，如父评论
 * reason：发送消息原因，如子评论
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MessageNotification extends BaseEntity {

    @TableId(value = "message_id", type = IdType.AUTO)
    private Long messageId;

    /** 触发消息通知的用户 */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Long uid;

    private String nickname;

    private Integer action;

    /** 消息通知所对应的主体 */
    @TableField(value = "main_id", updateStrategy = FieldStrategy.NEVER)
    private Long mainId;

    @TableField(value = "main_type", updateStrategy = FieldStrategy.NEVER)
    private Integer mainType;

    /** 消息通知的对象，而非消息通知所对应的主体的作者 */
    @TableField(value = "target_uid", updateStrategy = FieldStrategy.NEVER)
    private Long targetUid;

    @TableField(value = "target_id", updateStrategy = FieldStrategy.NEVER)
    private Long targetId;

    @TableField(value = "target_type", updateStrategy = FieldStrategy.NEVER)
    private Integer targetType;

    /** 对消息通知所对应的target的描述,可以为主体名称：文章标题，话题标题等，比如"我的文章《xxx》"、"我的评论'xxx'" */
    @TableField(value = "target_name", updateStrategy = FieldStrategy.NEVER)
    private String targetName;

}
