package com.heartape.hap.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.MessageNotification;
import com.heartape.hap.business.entity.bo.MessageNotificationBO;
import com.heartape.hap.business.entity.dto.MessageNotificationConsumerDTO;
import com.heartape.hap.business.entity.dto.MessageNotificationProducerDTO;

public interface IMessageNotificationService extends IService<MessageNotification> {

    /**
     * 创建点赞消息通知:验证mainId和targetId是否相关,将消息分配给相关人员
     * todo:推送至mq处理
     */
    void likeProducer(MessageNotificationProducerDTO messageNotificationProducerDTO);

    /**
     * 发送点赞消息通知
     * todo:推送至mq处理
     */
    void likeConsumer(MessageNotificationConsumerDTO messageNotificationConsumerDTO);

    /**
     * 消息通知列表
     */
    PageInfo<MessageNotificationBO> list(Integer pageNum, Integer pageSize);

}
