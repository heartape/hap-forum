package com.heartape.hap.mq.producer;

import com.heartape.hap.constant.MessageNotificationActionEnum;
import com.heartape.hap.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.entity.dto.MessageNotificationSendDTO;

public interface IMessageNotificationProducer {

    /**
     * 是否发送过消息通知
     */
    boolean exists(Long uid, Long mainId, MessageNotificationMainTypeEnum mainType, Long targetId, MessageNotificationTargetTypeEnum targetType, MessageNotificationActionEnum action);

    /**
     * 创建消息通知
     * todo:方法异步执行
     */
    void create(Long uid, String nickname, Long mainId, MessageNotificationMainTypeEnum mainType, Long targetId, MessageNotificationTargetTypeEnum targetType, MessageNotificationActionEnum actionEnum);

    /**
     * 分发消息通知
     */
    void send(MessageNotificationSendDTO messageNotificationSendDTO);

}
