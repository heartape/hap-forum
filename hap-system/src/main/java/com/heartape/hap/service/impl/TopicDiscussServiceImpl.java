package com.heartape.hap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.entity.bo.TopicDiscussBO;
import com.heartape.hap.entity.dto.TopicDiscussDTO;
import com.heartape.hap.exception.PermissionNoRemoveException;
import com.heartape.hap.exception.RelyDataNotExistedException;
import com.heartape.hap.feign.HapUserDetails;
import com.heartape.hap.feign.TokenFeignServiceImpl;
import com.heartape.hap.mapper.DiscussCommentChildMapper;
import com.heartape.hap.mapper.DiscussCommentMapper;
import com.heartape.hap.mapper.TopicDiscussMapper;
import com.heartape.hap.mapper.TopicMapper;
import com.heartape.hap.mq.producer.IMessageNotificationProducer;
import com.heartape.hap.service.ITopicDiscussService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.statistics.DiscussHotStatistics;
import com.heartape.hap.statistics.DiscussLikeStatistics;
import com.heartape.hap.statistics.HeatDeltaEnum;
import com.heartape.hap.statistics.TypeOperateStatistics;
import com.heartape.hap.utils.AssertUtils;
import com.heartape.hap.utils.StringTransformUtils;
import com.heartape.hap.entity.DiscussComment;
import com.heartape.hap.entity.DiscussCommentChild;
import com.heartape.hap.entity.Topic;
import com.heartape.hap.entity.TopicDiscuss;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @Autowired
    private DiscussHotStatistics discussHotStatistics;

    @Override
    public void create(TopicDiscussDTO topicDiscussDTO) {
        // 验证话题是否存在
        Long topicId = topicDiscussDTO.getTopicId();
        LambdaQueryWrapper<Topic> queryWrapper = new QueryWrapper<Topic>().lambda();
        Long count = topicMapper.selectCount(queryWrapper.eq(Topic::getTopicId, topicId));
        String message = "\nTopicDiscuss所依赖的Topic不存在,\ntopicId=" + topicId;
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(message));

        String content = topicDiscussDTO.getContent();
        String s = Jsoup.clean(content, Safelist.none());
        String ignoreBlank = stringTransformUtils.IgnoreBlank(s);
        int length = ignoreBlank.length();
        String simpleContent = length > 100 ? ignoreBlank.substring(0, 100) : content;

        TopicDiscuss topicDiscuss = new TopicDiscuss();
        BeanUtils.copyProperties(topicDiscussDTO, topicDiscuss);
        topicDiscuss.setSimpleContent(simpleContent);

        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String avatar = tokenInfo.getAvatar();
        String nickname = tokenInfo.getNickname();
        String profile = tokenInfo.getProfile();
        topicDiscuss.setUid(uid);
        topicDiscuss.setAvatar(avatar);
        topicDiscuss.setNickname(nickname);
        topicDiscuss.setProfile(profile);
        baseMapper.insert(topicDiscuss);
        // 热度
        Long discussId = topicDiscuss.getDiscussId();
        int hot = discussHotStatistics.updateIncrement(topicId, discussId, HeatDeltaEnum.DISCUSS_INIT.getDelta());
        log.info("topicId:" + topicId + ",discussId:" + discussId + ",设置初始热度为" + hot);
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
            int likeNumber = discussLikeStatistics.selectPositiveNumber(discussId);
            int dislikeNumber = discussLikeStatistics.selectNegativeNumber(discussId);
            topicDiscussBO.setLike(likeNumber);
            topicDiscussBO.setDislike(dislikeNumber);
            return topicDiscussBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(collect);
        return boPageInfo;
    }

    @Override
    public boolean like(Long discussId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        discussLikeStatistics.insert(discussId, uid, TypeOperateStatistics.TypeEnum.POSITIVE);
        if (true) {
            // 查询话题id
            LambdaQueryWrapper<TopicDiscuss> queryWrapper = new QueryWrapper<TopicDiscuss>().lambda();
            TopicDiscuss topicDiscuss = baseMapper.selectOne(queryWrapper.select(TopicDiscuss::getTopicId).eq(TopicDiscuss::getDiscussId, discussId));
            Long topicId = topicDiscuss.getTopicId();
            messageNotificationProducer.likeCreate(uid, nickname, discussId, MessageNotificationMainTypeEnum.TOPIC, topicId, MessageNotificationTargetTypeEnum.DISCUSS);
        }
        return true;
    }

    @Override
    public boolean dislike(Long discussId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        discussLikeStatistics.insert(discussId, uid, TypeOperateStatistics.TypeEnum.NEGATIVE);
        if (true) {
            // 查询话题id
            LambdaQueryWrapper<TopicDiscuss> queryWrapper = new QueryWrapper<TopicDiscuss>().lambda();
            TopicDiscuss topicDiscuss = baseMapper.selectOne(queryWrapper.select(TopicDiscuss::getTopicId).eq(TopicDiscuss::getDiscussId, discussId));
            Long topicId = topicDiscuss.getTopicId();
            messageNotificationProducer.dislikeCreate(uid, nickname, discussId, MessageNotificationMainTypeEnum.TOPIC, topicId, MessageNotificationTargetTypeEnum.DISCUSS);
        }
        return true;
    }

    @Override
    public void remove(Long discussId) {
        long uid = tokenFeignService.getUid();
        LambdaQueryWrapper<TopicDiscuss> queryWrapper = new QueryWrapper<TopicDiscuss>().lambda();
        int delete = baseMapper.delete(queryWrapper.eq(TopicDiscuss::getDiscussId, discussId).eq(TopicDiscuss::getUid, uid));
        String message = "\n没有删除权限,\ndiscussId=" + discussId + ",\nuid=" + uid;
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(message));
        // todo: rabbitmq异步删除
        LambdaQueryWrapper<DiscussComment> discussCommentWrapper = new QueryWrapper<DiscussComment>().lambda();
        LambdaQueryWrapper<DiscussCommentChild> discussCommentChildWrapper = new QueryWrapper<DiscussCommentChild>().lambda();

        discussCommentMapper.delete(discussCommentWrapper.eq(DiscussComment::getDiscussId, discussId));
        discussCommentChildMapper.delete(discussCommentChildWrapper.eq(DiscussCommentChild::getDiscussId, discussId));
    }
}
