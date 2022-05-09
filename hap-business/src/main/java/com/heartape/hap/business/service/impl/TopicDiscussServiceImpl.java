package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.business.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.business.entity.*;
import com.heartape.hap.business.entity.bo.TopicDiscussBO;
import com.heartape.hap.business.entity.dto.TopicDiscussDTO;
import com.heartape.hap.business.exception.PermissionNoRemoveException;
import com.heartape.hap.business.exception.RelyDataNotExistedException;
import com.heartape.hap.business.exception.ResourceOperateRepeatException;
import com.heartape.hap.business.feign.HapUserDetails;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import com.heartape.hap.business.mapper.DiscussCommentChildMapper;
import com.heartape.hap.business.mapper.DiscussCommentMapper;
import com.heartape.hap.business.mapper.TopicDiscussMapper;
import com.heartape.hap.business.mapper.TopicMapper;
import com.heartape.hap.business.mq.producer.IMessageNotificationProducer;
import com.heartape.hap.business.service.ITopicDiscussService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.business.statistics.DiscussLikeStatistics;
import com.heartape.hap.business.utils.AssertUtils;
import com.heartape.hap.business.utils.StringTransformUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@Service
public class TopicDiscussServiceImpl extends ServiceImpl<TopicDiscussMapper, TopicDiscuss> implements ITopicDiscussService {

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private DiscussCommentMapper discussCommentMapper;

    @Autowired
    private DiscussCommentChildMapper discussCommentChildMapper;

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Autowired
    private AssertUtils assertUtils;

    @Autowired
    private StringTransformUtils stringTransformUtils;

    @Autowired
    private IMessageNotificationProducer messageNotificationProducer;

    @Autowired
    private DiscussLikeStatistics discussLikeStatistics;

    @Override
    public void create(TopicDiscussDTO topicDiscussDTO) {
        // 验证话题是否存在
        Long topicId = topicDiscussDTO.getTopicId();
        Long count = topicMapper.selectCount(new QueryWrapper<Topic>().eq("topic_id", topicId));
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(String.format("TopicDiscuss所依赖的Topic:id=%s不存在", topicId)));

        String content = topicDiscussDTO.getContent();
        String s = Jsoup.clean(content, Safelist.none());
        String ignoreBlank = stringTransformUtils.IgnoreBlank(s);
        int length = ignoreBlank.length();
        String simpleContent = length > 100 ? ignoreBlank.substring(0, 100) : content;

        TopicDiscuss topicDiscuss = new TopicDiscuss();
        BeanUtils.copyProperties(topicDiscussDTO, topicDiscuss);
        topicDiscuss.setSimpleContent(simpleContent);
        baseMapper.insert(topicDiscuss);
    }

    @Override
    public PageInfo<TopicDiscussBO> list(Long topicId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<TopicDiscuss> topicDiscusses = baseMapper.selectWithCommentCount(topicId);
        PageInfo<TopicDiscuss> pageInfo = PageInfo.of(topicDiscusses);
        PageInfo<TopicDiscussBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, boPageInfo);
        List<TopicDiscussBO> collect = topicDiscusses.stream().map(topicDiscuss -> {
            TopicDiscussBO topicDiscussBO = new TopicDiscussBO();
            BeanUtils.copyProperties(topicDiscuss, topicDiscussBO);
            // 点赞
            Long discussId = topicDiscuss.getDiscussId();
            int likeNumber = discussLikeStatistics.getPositiveOperateNumber(discussId);
            int dislikeNumber = discussLikeStatistics.getPositiveOperateNumber(discussId);
            topicDiscussBO.setLike(likeNumber);
            topicDiscussBO.setDislike(dislikeNumber);
            return topicDiscussBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(collect);
        return boPageInfo;
    }

    @Override
    public void like(Long discussId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        boolean b = discussLikeStatistics.setPositiveOperate(discussId, uid);
        assertUtils.businessState(b, new ResourceOperateRepeatException("讨论:" + discussId + ",用户:" + uid + "已经进行过点赞"));
        // 查询话题id
        LambdaQueryWrapper<TopicDiscuss> queryWrapper = new QueryWrapper<TopicDiscuss>().lambda();
        TopicDiscuss topicDiscuss = baseMapper.selectOne(queryWrapper.select(TopicDiscuss::getTopicId).eq(TopicDiscuss::getDiscussId, discussId));
        Long topicId = topicDiscuss.getTopicId();
        messageNotificationProducer.likeCreate(uid, nickname, discussId, MessageNotificationMainTypeEnum.TOPIC, topicId, MessageNotificationTargetTypeEnum.DISCUSS);
    }

    @Override
    public void remove(Long discussId) {
        long uid = tokenFeignService.getUid();
        int delete = baseMapper.delete(new QueryWrapper<TopicDiscuss>().eq("discuss_id", discussId).eq("uid", uid));
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(String.format("没有删除权限,discussId:%s,uid:%s", discussId, uid)));
        // todo: 考虑是否需要rabbitmq异步删除
        discussCommentMapper.delete(new QueryWrapper<DiscussComment>().eq("discuss_id", discussId));
        discussCommentChildMapper.delete(new QueryWrapper<DiscussCommentChild>().eq("discuss_id", discussId));
    }
}
