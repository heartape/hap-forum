package com.heartape.hap.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessageNotificationTargetDTO {

    /** 收到消息通知的用户，而非消息通知所对应的主体的作者 */
    private List<Long> targetUid;

    private String targetName;
}
