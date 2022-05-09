package com.heartape.hap.business.constant;

/**
 * 定义rabbitMQ配置
 */
public class RabbitMqExchangeRouterConstant {

    /** 点对点 */
    public final static String DIRECT = "direct";
    /** 规则匹配 */
    public final static String TOPIC = "topic";
    /** 广播 */
    public final static String FANOUT = "fanout";

    /** ttl */
    public final static String X_MESSAGE_TTL_NAME = "x-message-ttl";
    /** ttl时间 */
    public final static String X_MESSAGE_TTL_VALUE = "5000";
    /** ttl时间数据类型 */
    public final static String X_MESSAGE_TTL_TYPE = "java.lang.Integer";

    public final static String MESSAGE_NOTIFICATION_LIKE_CREATE_EXCHANGE = "message_notification_exchange";
    public final static String MESSAGE_NOTIFICATION_LIKE_CREATE_EXCHANGE_TYPE = DIRECT;
    public final static String MESSAGE_NOTIFICATION_LIKE_CREATE_QUEUE = "message_notification_like_create_queue";
    public final static String MESSAGE_NOTIFICATION_LIKE_CREATE_ROUTING_KEY = "message_notification.like.create";

    public final static String MESSAGE_NOTIFICATION_DISLIKE_CREATE_EXCHANGE = "message_notification_exchange";
    public final static String MESSAGE_NOTIFICATION_DISLIKE_CREATE_EXCHANGE_TYPE = DIRECT;
    public final static String MESSAGE_NOTIFICATION_DISLIKE_CREATE_QUEUE = "message_notification_dislike_create_queue";
    public final static String MESSAGE_NOTIFICATION_DISLIKE_CREATE_ROUTING_KEY = "message_notification.dislike.create";

    public final static String MESSAGE_NOTIFICATION_LIKE_SEND_EXCHANGE = "message_notification_exchange";
    public final static String MESSAGE_NOTIFICATION_LIKE_SEND_EXCHANGE_TYPE = DIRECT;
    public final static String MESSAGE_NOTIFICATION_LIKE_SEND_QUEUE = "message_notification_like_send_queue";
    public final static String MESSAGE_NOTIFICATION_LIKE_SEND_ROUTING_KEY = "message_notification.like.send";

    public final static String MESSAGE_NOTIFICATION_DISLIKE_SEND_EXCHANGE = "message_notification_exchange";
    public final static String MESSAGE_NOTIFICATION_DISLIKE_SEND_EXCHANGE_TYPE = DIRECT;
    public final static String MESSAGE_NOTIFICATION_DISLIKE_SEND_QUEUE = "message_notification_dislike_send_queue";
    public final static String MESSAGE_NOTIFICATION_DISLIKE_SEND_ROUTING_KEY = "message_notification.dislike.send";
}
