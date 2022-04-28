package com.heartape.hap.business.entity.ro;

import lombok.Data;

@Data
public class MessageNotificationRO {

    /** 消息通知所对应的主体 */
    private Long mainId;

    private String mainType;

    /** 触发消息通知的用户 */
    private Long uid;

    private String targetType;

    /** 目标id */
    private Long targetId;
}
