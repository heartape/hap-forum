package com.heartape.hap.business.mq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Aspect
@Component
@Slf4j
public class RabbitConsumerAop {

    @Pointcut("@annotation(org.springframework.amqp.rabbit.annotation.RabbitListener)")
    public void pointCut(){}

    /**
     * 如果消息未进行ack操作，即忘了ack，那么该消息消费完之后，消息的状态为unacked，该消息也不能被其他消费者所消费；如果重启consumer，该消息将重新被消费。
     * 如果消息进行nack操作，即如下，将第三个参数requeue设置为true，此时消息将重新回到队列，mq将轮训其他的消费者。
     */
    @Around("pointCut()")
    public void listenerAround(ProceedingJoinPoint joinPoint) {
        Channel channel = null;
        Message message = null;

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Channel) {
                channel = (Channel) arg;
            } else if (arg instanceof Message) {
                message = (Message) arg;
            }
        }
        if (message==null || channel==null){
            log.warn("消息参数异常");
            return;
        }

        String messageId = message.getMessageProperties().getMessageId();
        String queue = message.getMessageProperties().getConsumerQueue();
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        String exchange = message.getMessageProperties().getReceivedExchange();
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        log.info( "\n"+"从rabbit接收消息:"
                + "\nMessageId:"+messageId
                + "\nQueue:"+queue
                + "\nRoutingKey:"+routingKey
                + "\nExchange:"+exchange);
        try {
            joinPoint.proceed();
            channel.basicAck(deliveryTag,false);
        } catch (Throwable e) {
            log.info("\nexception:{},\ncaused by:{}",e.getClass(),e.getMessage());
            e.printStackTrace();
            try {
                log.warn("消息消费失败,即将丢弃, MessageId:" + messageId);
                channel.basicNack(deliveryTag,false,false);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
