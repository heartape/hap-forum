package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.constant.MessageNotificationActionEnum;
import com.heartape.hap.business.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.business.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.business.constant.RabbitMqExchangeRouterConstant;
import com.heartape.hap.business.entity.*;
import com.heartape.hap.business.entity.bo.MessageNotificationBO;
import com.heartape.hap.business.entity.dto.MessageNotificationSendDTO;
import com.heartape.hap.business.entity.dto.MessageNotificationCreateDTO;
import com.heartape.hap.business.exception.ParamIsInvalidException;
import com.heartape.hap.business.exception.RelyDataNotExistedException;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import com.heartape.hap.business.mapper.*;
import com.heartape.hap.business.mq.producer.IMessageNotificationProducer;
import com.heartape.hap.business.service.IMessageNotificationService;
import com.heartape.hap.business.utils.AssertUtils;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageNotificationServiceImpl extends ServiceImpl<MessageNotificationMapper, MessageNotification> implements IMessageNotificationService {

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Autowired
    private AssertUtils assertUtils;

    @Autowired
    private CreatorMapper creatorMapper;

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    @Autowired
    private ArticleCommentChildMapper articleCommentChildMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private TopicDiscussMapper topicDiscussMapper;

    @Autowired
    private DiscussCommentMapper discussCommentMapper;

    @Autowired
    private DiscussCommentChildMapper discussCommentChildMapper;

    @Autowired
    private IMessageNotificationProducer messageNotificationProducer;

    /**
     * 创建点赞消息通知
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_LIKE_CREATE_QUEUE,
                    arguments = @Argument(
                            name = RabbitMqExchangeRouterConstant.X_MESSAGE_TTL_NAME,
                            value = RabbitMqExchangeRouterConstant.X_MESSAGE_TTL_VALUE,
                            type = RabbitMqExchangeRouterConstant.X_MESSAGE_TTL_TYPE)),
            exchange = @Exchange(value = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_LIKE_CREATE_EXCHANGE,
                    type = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_LIKE_CREATE_EXCHANGE_TYPE),
            key = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_LIKE_CREATE_ROUTING_KEY))
    public void likeMessageCreate(MessageNotificationCreateDTO messageNotificationCreateDTO, Channel channel, Message message) {
        List<MessageNotificationSendDTO> messageNotificationSendDTOS = messageCreate(messageNotificationCreateDTO);
        for (MessageNotificationSendDTO messageNotificationSendDTO : messageNotificationSendDTOS) {
            messageNotificationProducer.likeSend(messageNotificationSendDTO);
        }
    }

    /**
     * 创建踩消息通知
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_DISLIKE_CREATE_QUEUE,
                    arguments = @Argument(
                            name = RabbitMqExchangeRouterConstant.X_MESSAGE_TTL_NAME,
                            value = RabbitMqExchangeRouterConstant.X_MESSAGE_TTL_VALUE,
                            type = RabbitMqExchangeRouterConstant.X_MESSAGE_TTL_TYPE)),
            exchange = @Exchange(value = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_DISLIKE_CREATE_EXCHANGE,
                    type = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_DISLIKE_CREATE_EXCHANGE_TYPE),
            key = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_DISLIKE_CREATE_ROUTING_KEY))
    public void dislikeMessageCreate(MessageNotificationCreateDTO messageNotificationCreateDTO, Channel channel, Message message) {
        List<MessageNotificationSendDTO> messageNotificationSendDTOS = messageCreate(messageNotificationCreateDTO);
        for (MessageNotificationSendDTO messageNotificationSendDTO : messageNotificationSendDTOS) {
            messageNotificationProducer.dislikeSend(messageNotificationSendDTO);
        }
    }

    /**
     * 分发点赞消息通知
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_LIKE_SEND_QUEUE,
                    arguments = @Argument(
                            name = RabbitMqExchangeRouterConstant.X_MESSAGE_TTL_NAME,
                            value = RabbitMqExchangeRouterConstant.X_MESSAGE_TTL_VALUE,
                            type = RabbitMqExchangeRouterConstant.X_MESSAGE_TTL_TYPE)),
            exchange = @Exchange(value = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_LIKE_SEND_EXCHANGE,
                    type = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_LIKE_SEND_EXCHANGE_TYPE),
            key = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_LIKE_SEND_ROUTING_KEY))
    public void likeMessageSend(MessageNotificationSendDTO messageNotificationSendDTO, Channel channel, Message message) {
        MessageNotification messageNotification = new MessageNotification();
        BeanUtils.copyProperties(messageNotificationSendDTO, messageNotification);
        baseMapper.insert(messageNotification);
    }

    /**
     * 分发踩消息通知
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_DISLIKE_SEND_QUEUE,
                    arguments = @Argument(
                            name = RabbitMqExchangeRouterConstant.X_MESSAGE_TTL_NAME,
                            value = RabbitMqExchangeRouterConstant.X_MESSAGE_TTL_VALUE,
                            type = RabbitMqExchangeRouterConstant.X_MESSAGE_TTL_TYPE)),
            exchange = @Exchange(value = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_DISLIKE_SEND_EXCHANGE,
                    type = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_DISLIKE_SEND_EXCHANGE_TYPE),
            key = RabbitMqExchangeRouterConstant.MESSAGE_NOTIFICATION_DISLIKE_SEND_ROUTING_KEY))
    public void dislikeMessageSend(MessageNotificationSendDTO messageNotificationSendDTO, Channel channel, Message message) {
        MessageNotification messageNotification = new MessageNotification();
        BeanUtils.copyProperties(messageNotificationSendDTO, messageNotification);
        baseMapper.insert(messageNotification);
    }

    /**
     * 检查main和target的关联关系,
     * target是否存在,
     * 查询目标targetName,
     * 创建分发消息
     */
    private List<MessageNotificationSendDTO> messageCreate(MessageNotificationCreateDTO messageNotificationCreateDTO) {
        Long mainId = messageNotificationCreateDTO.getMainId();
        Long targetId = messageNotificationCreateDTO.getTargetId();
        Integer targetType = messageNotificationCreateDTO.getTargetType();

        List<Long> targetUid = new ArrayList<>();
        String targetName;
        if (MessageNotificationTargetTypeEnum.CREATOR.getTypeCode().equals(targetType)) {
            Long count = creatorMapper.selectCount(new QueryWrapper<Creator>().lambda().eq(Creator::getUid, targetId));
            // mainId与targetId相等且账户存在
            checkRelation(mainId.equals(targetId) && count == 1, mainId, targetId, targetType);
            targetName = creatorMapper.selectOne(new QueryWrapper<Creator>().lambda().select(Creator::getNickname).eq(Creator::getUid, targetId)).getNickname();
            // 加入target用户
            targetUid.add(targetId);
        } else if (MessageNotificationTargetTypeEnum.LABEL.getTypeCode().equals(targetType)) {
            Long count = labelMapper.selectCount(new QueryWrapper<Label>().lambda().eq(Label::getLabelId, targetId));
            // mainId与targetId相等且标签存在
            checkRelation(mainId.equals(targetId) && count == 1, mainId, targetId, targetType);
            targetName = labelMapper.selectOne(new QueryWrapper<Label>().lambda().select(Label::getName).eq(Label::getLabelId, targetId)).getName();
            // todo:加入关注标签的用户
        } else if (MessageNotificationTargetTypeEnum.ARTICLE.getTypeCode().equals(targetType)) {
            Long count = articleMapper.selectCount(new QueryWrapper<Article>().lambda().eq(Article::getArticleId, targetId));
            // mainId与targetId相等且文章存在
            checkRelation(mainId.equals(targetId) && count == 1, mainId, targetId, targetType);
            Article article = articleMapper.selectOne(new QueryWrapper<Article>().lambda().select(Article::getTitle, Article::getUid).eq(Article::getArticleId, targetId));
            targetName = article.getTitle();
            // 加入文章作者
            Long uid = article.getUid();
            targetUid.add(uid);
        } else if (MessageNotificationTargetTypeEnum.ARTICLE_COMMENT.getTypeCode().equals(targetType)) {
            Long count = articleCommentMapper.selectCount(new QueryWrapper<ArticleComment>().lambda().eq(ArticleComment::getArticleId, mainId).eq(ArticleComment::getCommentId, targetId));
            // 评论存在且与文章相关联
            checkRelation(count == 1, mainId, targetId, targetType);
            ArticleComment articleComment = articleCommentMapper.selectOne(new QueryWrapper<ArticleComment>().lambda().select(ArticleComment::getContent, ArticleComment::getUid).eq(ArticleComment::getCommentId, targetId));
            targetName = articleComment.getContent();
            // 加入评论作者
            Long uid = articleComment.getUid();
            targetUid.add(uid);
        } else if (MessageNotificationTargetTypeEnum.ARTICLE_COMMENT_CHILD.getTypeCode().equals(targetType)) {
            Long count = articleCommentChildMapper.selectCount(new QueryWrapper<ArticleCommentChild>().lambda().eq(ArticleCommentChild::getArticleId, mainId).eq(ArticleCommentChild::getCommentId, targetId));
            // 评论存在且与文章相关联
            checkRelation(count == 1, mainId, targetId, targetType);
            ArticleCommentChild articleCommentChild = articleCommentChildMapper.selectOne(new QueryWrapper<ArticleCommentChild>().lambda().select(ArticleCommentChild::getContent, ArticleCommentChild::getUid).eq(ArticleCommentChild::getCommentId, targetId));
            targetName = articleCommentChild.getContent();
            // 加入评论作者
            Long uid = articleCommentChild.getUid();
            targetUid.add(uid);
        } else if (MessageNotificationTargetTypeEnum.TOPIC.getTypeCode().equals(targetType)) {
            Long count = topicMapper.selectCount(new QueryWrapper<Topic>().lambda().eq(Topic::getTopicId, targetId));
            // mainId与targetId相等且话题存在
            checkRelation(mainId.equals(targetId) && count == 1, mainId, targetId, targetType);
            targetName = topicMapper.selectOne(new QueryWrapper<Topic>().lambda().select(Topic::getTitle).eq(Topic::getTopicId, targetId)).getTitle();
            // todo:加入关注话题用户
        } else if (MessageNotificationTargetTypeEnum.DISCUSS.getTypeCode().equals(targetType)) {
            Long count = topicDiscussMapper.selectCount(new QueryWrapper<TopicDiscuss>().lambda().eq(TopicDiscuss::getTopicId, mainId).eq(TopicDiscuss::getDiscussId, targetId));
            // 讨论存在且与话题相关联
            checkRelation(count == 1, mainId, targetId, targetType);
            TopicDiscuss topicDiscuss = topicDiscussMapper.selectOne(new QueryWrapper<TopicDiscuss>().lambda().select(TopicDiscuss::getSimpleContent, TopicDiscuss::getUid).eq(TopicDiscuss::getDiscussId, targetId));
            targetName = topicDiscuss.getSimpleContent();
            // 加入讨论作者
            Long uid = topicDiscuss.getUid();
            targetUid.add(uid);
        } else if (MessageNotificationTargetTypeEnum.DISCUSS_COMMENT.getTypeCode().equals(targetType)) {
            Long count = discussCommentMapper.selectCount(new QueryWrapper<DiscussComment>().lambda().eq(DiscussComment::getTopicId, mainId).eq(DiscussComment::getCommentId, targetId));
            // 评论存在且与话题相关联
            checkRelation(count == 1, mainId, targetId, targetType);
            DiscussComment discussComment = discussCommentMapper.selectOne(new QueryWrapper<DiscussComment>().lambda().select(DiscussComment::getContent, DiscussComment::getUid).eq(DiscussComment::getCommentId, targetId));
            targetName = discussComment.getContent();
            // 加入评论作者
            Long uid = discussComment.getUid();
            targetUid.add(uid);
        } else if (MessageNotificationTargetTypeEnum.DISCUSS_COMMENT_CHILD.getTypeCode().equals(targetType)) {
            Long count = discussCommentChildMapper.selectCount(new QueryWrapper<DiscussCommentChild>().lambda().eq(DiscussCommentChild::getTopicId, mainId).eq(DiscussCommentChild::getCommentId, targetId));
            // 评论存在且与话题相关联
            checkRelation(count == 1, mainId, targetId, targetType);
            DiscussCommentChild discussCommentChild = discussCommentChildMapper.selectOne(new QueryWrapper<DiscussCommentChild>().lambda().select(DiscussCommentChild::getContent, DiscussCommentChild::getUid).eq(DiscussCommentChild::getCommentId, targetId));
            targetName = discussCommentChild.getContent();
            // 加入评论作者
            Long uid = discussCommentChild.getUid();
            targetUid.add(uid);
        } else {
            throw new ParamIsInvalidException("targetType:" + targetType + ",不是有效的类型");
        }

        assertUtils.businessState(!CollectionUtils.isEmpty(targetUid), new RelyDataNotExistedException("mainId:" + mainId + ",targetId:" + targetId + ",targetType:" + targetType + ",未找到需要通知的用户"));

        return targetUid.stream().map(item -> {
            MessageNotificationSendDTO messageNotificationSendDTO = new MessageNotificationSendDTO();
            BeanUtils.copyProperties(messageNotificationCreateDTO, messageNotificationSendDTO);
            messageNotificationSendDTO.setTargetName(targetName);
            messageNotificationSendDTO.setTargetUid(item);
            return messageNotificationSendDTO;
        }).collect(Collectors.toList());
    }

    /**
     * 关联关系判定
     */
    private void checkRelation(boolean expression, Long mainId, Long targetId, Integer targetType) {
        assertUtils.businessState(expression, new ParamIsInvalidException("mainId:" + mainId + ",targetId:" + targetId + ",targetType:" + targetType + ",通知主体与目标非关联"));
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

            Integer actionCode = messageNotification.getAction();
            Integer mainTypeCode = messageNotification.getMainType();
            Integer targetTypeCode = messageNotification.getTargetType();

            String action = MessageNotificationActionEnum.exchangeAction(actionCode);
            String mainType = MessageNotificationMainTypeEnum.exchangeMainType(mainTypeCode);
            String targetType = MessageNotificationTargetTypeEnum.exchangeTargetType(targetTypeCode);

            messageNotificationBO.setAction(action);
            messageNotificationBO.setMainType(mainType);
            messageNotificationBO.setTargetType(targetType);

            return messageNotificationBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(messageNotificationBOS);
        return boPageInfo;
    }
}
