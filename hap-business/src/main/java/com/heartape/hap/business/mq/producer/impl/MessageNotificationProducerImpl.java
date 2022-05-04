package com.heartape.hap.business.mq.producer.impl;

import com.heartape.hap.business.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.business.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.business.constant.RabbitMqExchangeRouterConstant;
import com.heartape.hap.business.entity.dto.MessageNotificationCreateDTO;
import com.heartape.hap.business.entity.dto.MessageNotificationSendDTO;
import com.heartape.hap.business.mq.RabbitMQProducer;
import com.heartape.hap.business.mq.producer.IMessageNotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageNotificationProducerImpl implements IMessageNotificationProducer {

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Override
    public void likeCreate(Long uid, Long mainId, MessageNotificationMainTypeEnum mainType, Long targetId, MessageNotificationTargetTypeEnum targetType) {
        MessageNotificationCreateDTO messageNotificationCreateDTO = new MessageNotificationCreateDTO();
        String exchange = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_CREATE_EXCHANGE;
        String routingKey = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_CREATE_ROUTING_KEY;
        rabbitMQProducer.sendMessage(exchange, routingKey, messageNotificationCreateDTO);
    }

    @Override
    public void likeSend(Long uid, Long mainId, String mainType, Long targetId, String targetType, Long targetUid, String targetName) {
        MessageNotificationSendDTO messageNotificationSendDTO = new MessageNotificationSendDTO();
        String exchange = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_SEND_EXCHANGE;
        String routingKey = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_SEND_ROUTING_KEY;
        rabbitMQProducer.sendMessage(exchange, routingKey, messageNotificationSendDTO);
    }
}
