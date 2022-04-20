package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.constant.MessageNotificationActionEnum;
import com.heartape.hap.business.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.business.constant.MessageNotificationTargetTypeEnum;
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
import java.util.stream.Collectors;

@Service
public class MessageNotificationServiceImpl extends ServiceImpl<MessageNotificationMapper, MessageNotification> implements IMessageNotificationService {

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Override
    public void createLike(MessageNotificationDTO messageNotificationDTO) {
        // todo:对
        MessageNotification messageNotification = new MessageNotification();
        BeanUtils.copyProperties(messageNotificationDTO, messageNotification);
        messageNotification.setAction(MessageNotificationActionEnum.LIKE.getCode());
        save(messageNotification);
    }

    @Override
    public PageInfo<MessageNotificationBO> list(Integer pageNum, Integer pageSize) {
        Long targetUid = tokenFeignService.getUid();
        PageHelper.startPage(pageNum, pageSize);
        List<MessageNotification> messageNotifications = query()
                .select("message_id", "uid", "nickname", "main_id", "main_type", "action", "target_name", "created_time")
                .eq("target_uid", targetUid).list();
        PageInfo<MessageNotification> pageInfo = PageInfo.of(messageNotifications);
        PageInfo<MessageNotificationBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, boPageInfo);

        List<MessageNotificationBO> messageNotificationBOS = messageNotifications.stream().map(messageNotification -> {
            MessageNotificationBO messageNotificationBO = new MessageNotificationBO();
            BeanUtils.copyProperties(messageNotification, messageNotificationBO);
            exchangeTargetType(messageNotificationBO);
            return messageNotificationBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(messageNotificationBOS);
        return boPageInfo;
    }

    /**
     * 将action, mainType, targetType转化为对应的中文
     */
    private void exchangeTargetType(MessageNotificationBO messageNotificationBO) {
        String action = messageNotificationBO.getAction();
        String mainType = messageNotificationBO.getMainType();
        String targetType = messageNotificationBO.getTargetType();
        // action
        if (MessageNotificationActionEnum.LIKE.getCode().equals(action)) {
            messageNotificationBO.setAction(MessageNotificationActionEnum.LIKE.getName());
        } else if (MessageNotificationActionEnum.STAR.getCode().equals(action)) {
            messageNotificationBO.setAction(MessageNotificationActionEnum.STAR.getName());
        } else if (MessageNotificationActionEnum.DISCUSS.getCode().equals(action)) {
            messageNotificationBO.setAction(MessageNotificationActionEnum.DISCUSS.getName());
        } else if (MessageNotificationActionEnum.COMMENT.getCode().equals(action)) {
            messageNotificationBO.setAction(MessageNotificationActionEnum.COMMENT.getName());
        } else if (MessageNotificationActionEnum.REPLY.getCode().equals(action)) {
            messageNotificationBO.setAction(MessageNotificationActionEnum.REPLY.getName());
        }

        // mainType
        if (MessageNotificationMainTypeEnum.ARTICLE.getTypeCode().equals(mainType)) {
            messageNotificationBO.setMainType(MessageNotificationMainTypeEnum.ARTICLE.getTypeName());
        } else if (MessageNotificationMainTypeEnum.TOPIC.getTypeCode().equals(mainType)) {
            messageNotificationBO.setMainType(MessageNotificationMainTypeEnum.TOPIC.getTypeName());
        } else if (MessageNotificationMainTypeEnum.LABEL.getTypeCode().equals(mainType)) {
            messageNotificationBO.setMainType(MessageNotificationMainTypeEnum.LABEL.getTypeName());
        } else if (MessageNotificationMainTypeEnum.CREATOR.getTypeCode().equals(mainType)) {
            messageNotificationBO.setMainType(MessageNotificationMainTypeEnum.CREATOR.getTypeName());
        }

        // targetType
        if (MessageNotificationTargetTypeEnum.ARTICLE.getTypeCode().equals(targetType)) {
            messageNotificationBO.setTargetType(MessageNotificationTargetTypeEnum.ARTICLE.getTypeName());
        } else if (MessageNotificationTargetTypeEnum.ARTICLE_COMMENT.getTypeCode().equals(targetType)) {
            messageNotificationBO.setTargetType(MessageNotificationTargetTypeEnum.ARTICLE_COMMENT.getTypeName());
        } else if (MessageNotificationTargetTypeEnum.ARTICLE_COMMENT_CHILD.getTypeCode().equals(targetType)) {
            messageNotificationBO.setTargetType(MessageNotificationTargetTypeEnum.ARTICLE_COMMENT_CHILD.getTypeName());
        } else if (MessageNotificationTargetTypeEnum.TOPIC.getTypeCode().equals(targetType)) {
            messageNotificationBO.setTargetType(MessageNotificationTargetTypeEnum.TOPIC.getTypeName());
        } else if (MessageNotificationTargetTypeEnum.DISCUSS.getTypeCode().equals(targetType)) {
            messageNotificationBO.setTargetType(MessageNotificationTargetTypeEnum.DISCUSS.getTypeName());
        } else if (MessageNotificationTargetTypeEnum.DISCUSS_COMMENT.getTypeCode().equals(targetType)) {
            messageNotificationBO.setTargetType(MessageNotificationTargetTypeEnum.DISCUSS_COMMENT.getTypeName());
        } else if (MessageNotificationTargetTypeEnum.DISCUSS_COMMENT_CHILD.getTypeCode().equals(targetType)) {
            messageNotificationBO.setTargetType(MessageNotificationTargetTypeEnum.DISCUSS_COMMENT_CHILD.getTypeName());
        } else if (MessageNotificationTargetTypeEnum.LABEL.getTypeCode().equals(targetType)) {
            messageNotificationBO.setTargetType(MessageNotificationTargetTypeEnum.LABEL.getTypeName());
        } else if (MessageNotificationTargetTypeEnum.CREATOR.getTypeCode().equals(targetType)) {
            messageNotificationBO.setTargetType(MessageNotificationTargetTypeEnum.CREATOR.getTypeName());
        }
    }
}
