package com.heartape.hap.entity.dto;

import lombok.Data;

@Data
public class MessageNotificationCreateDTO {

    /** 消息通知所对应的主体 */
    private Long mainId;

    private Integer mainType;

    /** 触发消息通知的用户 */
    private Long uid;

    private String nickname;

    private Integer targetType;

    /** 目标id */
    private Long targetId;

    /** 目标id */
    private Integer action;
}
