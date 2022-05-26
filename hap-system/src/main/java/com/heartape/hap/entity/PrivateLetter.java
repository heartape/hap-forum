package com.heartape.hap.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PrivateLetter extends BaseEntity {

    @TableId(value = "letter_id", type = IdType.AUTO)
    private Long letterId;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Long uid;

    private String avatar;

    private String nickname;

    @TableField(value = "target_uid", updateStrategy = FieldStrategy.NEVER)
    private Long targetUid;

    @TableField(value = "simple_content", updateStrategy = FieldStrategy.NEVER)
    private String simpleContent;

    private String content;

    @TableField(value = "look_up")
    private Boolean lookUp;

    private Integer unread;

}
