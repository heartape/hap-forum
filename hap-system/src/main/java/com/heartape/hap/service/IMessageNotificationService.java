package com.heartape.hap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.entity.MessageNotification;
import com.heartape.hap.entity.bo.MessageNotificationBO;

public interface IMessageNotificationService extends IService<MessageNotification> {

    /**
     * 消息通知列表
     */
    PageInfo<MessageNotificationBO> list(Integer pageNum, Integer pageSize);

}
