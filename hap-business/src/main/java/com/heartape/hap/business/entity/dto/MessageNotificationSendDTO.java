package com.heartape.hap.business.entity.dto;

import lombok.Data;

@Data
public class MessageNotificationSendDTO {

    /** 消息通知所对应的主体 */
    private Long mainId;

    private String mainType;

    /** 触发消息通知的用户 */
    private Long uid;

    /** 收到消息通知的用户，而非消息通知所对应的主体的作者 */
    private Long targetUid;

    /** 目标id */
    private Long targetId;

    private String targetType;

    private String targetName;
}
