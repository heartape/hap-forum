package com.heartape.hap.mq;

public interface RabbitMQProducer {

    /**
     * 发送消息
     */
    <T> void sendMessage(String exchange, String routingKey, T o);

}
