package com.heartape.hap.business.mq.producer;

import com.heartape.hap.business.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.business.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.business.entity.dto.MessageNotificationSendDTO;

public interface IMessageNotificationProducer {

    /**
     * 创建点赞消息通知
     * todo:方法异步执行
     */
    void likeCreate(Long uid, String nickname, Long mainId, MessageNotificationMainTypeEnum mainType, Long targetId, MessageNotificationTargetTypeEnum targetType);

    /**
     * 创建点踩消息通知
     * todo:方法异步执行
     */
    void dislikeCreate(Long uid, String nickname, Long mainId, MessageNotificationMainTypeEnum mainType, Long targetId, MessageNotificationTargetTypeEnum targetType);

    /**
     * 分发点赞消息通知
     */
    void likeSend(MessageNotificationSendDTO messageNotificationSendDTO);

    /**
     * 分发点赞消息通知
     */
    void dislikeSend(MessageNotificationSendDTO messageNotificationSendDTO);

}
