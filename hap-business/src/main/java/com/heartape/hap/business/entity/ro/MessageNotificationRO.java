package com.heartape.hap.business.entity.ro;

import lombok.Data;

@Data
public class MessageNotificationRO {

    /** 消息通知所对应的主体 */
    private Long mainId;

    private String mainType;

    /** 消息通知的对象，而非消息通知所对应的主体的作者 */
    private Long targetUid;

    private String targetType;

    private String targetName;
}
