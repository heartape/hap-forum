package com.heartape.hap.business.mq.producer;

import com.heartape.hap.business.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.business.constant.MessageNotificationTargetTypeEnum;

public interface IMessageNotificationProducer {

    /**
     * 创建点赞消息通知
     */
    void likeCreate(Long uid, Long mainId, MessageNotificationMainTypeEnum mainType, Long targetId, MessageNotificationTargetTypeEnum targetType);

    /**
     * 分发点赞消息通知
     */
    void likeSend(Long uid, Long mainId, String mainType, Long targetId, String targetType, Long targetUid, String targetName);

}
