package com.heartape.hap.mq.producer.impl;

import com.heartape.hap.constant.MessageNotificationActionEnum;
import com.heartape.hap.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.constant.RabbitMqExchangeRouterConstant;
import com.heartape.hap.entity.dto.MessageNotificationCreateDTO;
import com.heartape.hap.entity.dto.MessageNotificationSendDTO;
import com.heartape.hap.mq.RabbitMQProducer;
import com.heartape.hap.mq.producer.IMessageNotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageNotificationProducerImpl implements IMessageNotificationProducer {

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Override
    public void likeCreate(Long uid, String nickname, Long mainId, MessageNotificationMainTypeEnum mainType, Long targetId, MessageNotificationTargetTypeEnum targetType) {
        // todo:创建消息通知流水表，用于防止重复发送
        // todo:未重复发送就增加热度
        String exchange = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_LIKE_CREATE_EXCHANGE;
        String routingKey = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_LIKE_CREATE_ROUTING_KEY;
        MessageNotificationCreateDTO messageNotificationCreateDTO = create(uid, nickname, mainId, mainType, targetId, targetType, MessageNotificationActionEnum.LIKE);
        rabbitMQProducer.sendMessage(exchange, routingKey, messageNotificationCreateDTO);
    }

    @Override
    public void dislikeCreate(Long uid, String nickname, Long mainId, MessageNotificationMainTypeEnum mainType, Long targetId, MessageNotificationTargetTypeEnum targetType) {
        // todo:创建消息通知流水表，用于防止重复发送
        // todo:未重复发送就增加热度
        String exchange = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_DISLIKE_CREATE_EXCHANGE;
        String routingKey = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_DISLIKE_CREATE_ROUTING_KEY;
        MessageNotificationCreateDTO messageNotificationCreateDTO = create(uid, nickname, mainId, mainType, targetId, targetType, MessageNotificationActionEnum.DISLIKE);
        rabbitMQProducer.sendMessage(exchange, routingKey, messageNotificationCreateDTO);
    }

    private MessageNotificationCreateDTO create(Long uid, String nickname, Long mainId, MessageNotificationMainTypeEnum mainType, Long targetId, MessageNotificationTargetTypeEnum targetType, MessageNotificationActionEnum actionEnum) {
        MessageNotificationCreateDTO messageNotificationCreateDTO = new MessageNotificationCreateDTO();
        messageNotificationCreateDTO.setAction(actionEnum.getCode());
        messageNotificationCreateDTO.setUid(uid);
        messageNotificationCreateDTO.setNickname(nickname);
        messageNotificationCreateDTO.setMainId(mainId);
        messageNotificationCreateDTO.setMainType(mainType.getTypeCode());
        messageNotificationCreateDTO.setTargetId(targetId);
        messageNotificationCreateDTO.setTargetType(targetType.getTypeCode());
        return messageNotificationCreateDTO;
    }

    @Override
    public void likeSend(MessageNotificationSendDTO messageNotificationSendDTO) {
        String exchange = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_LIKE_SEND_EXCHANGE;
        String routingKey = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_LIKE_SEND_ROUTING_KEY;
        rabbitMQProducer.sendMessage(exchange, routingKey, messageNotificationSendDTO);
    }

    @Override
    public void dislikeSend(MessageNotificationSendDTO messageNotificationSendDTO) {
        String exchange = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_DISLIKE_SEND_EXCHANGE;
        String routingKey = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_DISLIKE_SEND_ROUTING_KEY;
        rabbitMQProducer.sendMessage(exchange, routingKey, messageNotificationSendDTO);
    }
}
