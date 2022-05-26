package com.heartape.hap.mq;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultRabbitMQProducer implements RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public <T> void sendMessage(String exchange, String routingKey, T o) {
        String id = UUID.randomUUID().toString();

        MessagePostProcessor messagePostProcessor = message->{
            MessageProperties messageProperties = message.getMessageProperties();
            messageProperties.setContentEncoding("utf8");
            messageProperties.setMessageId(id);
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            return message;
        };

        CorrelationData correlationData = new CorrelationData(id);
        rabbitTemplate.convertAndSend(exchange, routingKey, o, messagePostProcessor, correlationData);
    }
}
