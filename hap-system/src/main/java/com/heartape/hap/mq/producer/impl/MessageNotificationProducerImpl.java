package com.heartape.hap.mq.producer.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heartape.hap.constant.MessageNotificationActionEnum;
import com.heartape.hap.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.constant.RabbitMqExchangeRouterConstant;
import com.heartape.hap.entity.MessageNotification;
import com.heartape.hap.entity.dto.MessageNotificationCreateDTO;
import com.heartape.hap.entity.dto.MessageNotificationSendDTO;
import com.heartape.hap.mapper.MessageNotificationMapper;
import com.heartape.hap.mq.RabbitMQProducer;
import com.heartape.hap.mq.producer.IMessageNotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageNotificationProducerImpl implements IMessageNotificationProducer {

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Autowired
    private MessageNotificationMapper notificationMapper;

    @Override
    public boolean exists(Long uid, Long mainId, MessageNotificationMainTypeEnum mainType, Long targetId, MessageNotificationTargetTypeEnum targetType, MessageNotificationActionEnum action) {
        LambdaQueryWrapper<MessageNotification> queryWrapper = new QueryWrapper<MessageNotification>().lambda();
        queryWrapper
                .eq(MessageNotification::getUid, uid)
                .eq(MessageNotification::getMainId, mainId)
                .eq(MessageNotification::getMainType, mainType.getTypeCode())
                .eq(MessageNotification::getTargetId, targetId)
                .eq(MessageNotification::getTargetType, targetType.getTypeCode());
        return notificationMapper.exists(queryWrapper);
    }

    @Override
    public void create(Long uid, String nickname, Long mainId, MessageNotificationMainTypeEnum mainType, Long targetId, MessageNotificationTargetTypeEnum targetType, MessageNotificationActionEnum actionEnum) {
        String exchange = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_CREATE_EXCHANGE;
        String routingKey = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_CREATE_ROUTING_KEY;
        MessageNotificationCreateDTO messageNotificationCreateDTO = exchange(uid, nickname, mainId, mainType, targetId, targetType, actionEnum);
        rabbitMQProducer.sendMessage(exchange, routingKey, messageNotificationCreateDTO);
    }

    private MessageNotificationCreateDTO exchange(Long uid, String nickname, Long mainId, MessageNotificationMainTypeEnum mainType, Long targetId, MessageNotificationTargetTypeEnum targetType, MessageNotificationActionEnum actionEnum) {
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
    public void send(MessageNotificationSendDTO messageNotificationSendDTO) {
        String exchange = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_SEND_EXCHANGE;
        String routingKey = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_SEND_ROUTING_KEY;
        rabbitMQProducer.sendMessage(exchange, routingKey, messageNotificationSendDTO);
    }
}
