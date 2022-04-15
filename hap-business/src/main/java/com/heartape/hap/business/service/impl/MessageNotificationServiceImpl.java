package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.constant.MessageNotificationActionEnum;
import com.heartape.hap.business.entity.MessageNotification;
import com.heartape.hap.business.entity.bo.MessageNotificationBO;
import com.heartape.hap.business.entity.dto.MessageNotificationDTO;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import com.heartape.hap.business.mapper.MessageNotificationMapper;
import com.heartape.hap.business.service.IMessageNotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageNotificationServiceImpl extends ServiceImpl<MessageNotificationMapper, MessageNotification> implements IMessageNotificationService {

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Override
    public void createLike(MessageNotificationDTO messageNotificationDTO) {
        // todo:对
        MessageNotification messageNotification = new MessageNotification();
        BeanUtils.copyProperties(messageNotificationDTO, messageNotification);
        messageNotification.setAction(MessageNotificationActionEnum.LIKE.getAction());
        save(messageNotification);
    }

    @Override
    public PageInfo<MessageNotificationBO> list(Integer pageNum, Integer pageSize) {
        Long targetUid = tokenFeignService.getUid();
        PageHelper.startPage(pageNum, pageSize);
        List<MessageNotification> messageNotifications = query().
                select("message_id", "uid", "nickname", "main_id", "main_type", "action", "target_name", "created_time")
                .eq("target_uid", targetUid).list();
        return null;
    }
}
