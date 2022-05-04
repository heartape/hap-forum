package com.heartape.hap.business.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnsCallback;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
@EnableRabbit
public class RabbitConfig implements ConfirmCallback, ReturnsCallback {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * true:当交换机无法路由消息时，把消息返回给生产者
     * false:当交换机无法路由消息时，直接丢弃消息
     */
    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
        rabbitTemplate.setMandatory(true);
    }

    /**
     * 交换机回调(消息发送到交换机)
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (correlationData == null) {
            return;
        }
        String messageId = correlationData.getId();
        if (ack){
            log.info( "\nconfirm回调>>>交换机收到消息\nmessageId:"+messageId);
        } else {
            log.info( "\nconfirm回调>>>交换机未收到消息\nmessageId:"+messageId+"\n原因:"+cause);
        }
    }

    /**
     * 路由回调(消息无法路由)
     * 延时插件会导致一直调用该回调，需要处理
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {

        log.info( "\n"+"returnedMessage回调:"
                + "\nMessageId:"+returnedMessage.getMessage().getMessageProperties().getHeader("spring_returned_message_correlation")
                + "\nRoutingKey:"+returnedMessage.getRoutingKey()
                + "\nExchange:"+returnedMessage.getExchange()
                + "\ncause:"+returnedMessage.getReplyText());
    }
}
