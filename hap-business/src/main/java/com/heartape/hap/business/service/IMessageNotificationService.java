package com.heartape.hap.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.MessageNotification;
import com.heartape.hap.business.entity.bo.MessageNotificationBO;
import com.heartape.hap.business.entity.dto.MessageNotificationDTO;

public interface IMessageNotificationService extends IService<MessageNotification> {

    /**
     * 创建点赞消息通知
     */
    void createLike(MessageNotificationDTO messageNotificationDTO);

    /**
     * 消息通知列表
     */
    PageInfo<MessageNotificationBO> list(Integer pageNum, Integer pageSize);
}
