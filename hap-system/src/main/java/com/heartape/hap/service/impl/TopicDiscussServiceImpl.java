package com.heartape.hap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.constant.HeatDeltaEnum;
import com.heartape.hap.constant.MessageNotificationActionEnum;
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
import com.heartape.hap.statistics.AbstractTypeOperateStatistics;
import com.heartape.hap.statistics.DiscussHotStatistics;
import com.heartape.hap.statistics.DiscussLikeStatistics;
import com.heartape.hap.statistics.TopicHotStatistics;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  ???????????????
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
    private TopicHotStatistics topicHotStatistics;

    @Autowired
    private DiscussHotStatistics discussHotStatistics;

    @Caching(evict = {
            @CacheEvict(value = "di-hot", cacheManager = "caffeineCacheManager", key = "#topicDiscussDTO.topicId + ':1:10'"),
            @CacheEvict(value = "di-hot", cacheManager = "caffeineCacheManager", key = "#topicDiscussDTO.topicId + ':2:10'")
    })
    @Override
    public void create(TopicDiscussDTO topicDiscussDTO) {
        // ????????????????????????
        Long topicId = topicDiscussDTO.getTopicId();
        LambdaQueryWrapper<Topic> queryWrapper = new QueryWrapper<Topic>().lambda();
        Long count = topicMapper.selectCount(queryWrapper.eq(Topic::getTopicId, topicId));
        String message = "\nTopicDiscuss????????????Topic?????????,\ntopicId=" + topicId;
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
        // ??????
        Long discussId = topicDiscuss.getDiscussId();
        int hot = discussHotStatistics.updateIncrement(topicId, discussId, HeatDeltaEnum.DISCUSS_INIT.getDelta());
        log.info("topicId:{},discussId:{},?????????????????????:{}", topicId, discussId, hot);
        int increment = topicHotStatistics.updateIncrement(topicId, HeatDeltaEnum.DISCUSS_INIT.getDelta());
        log.info("???????????????topicId???{},???????????????{}", topicId, increment);
    }

    @Cacheable(value = "di-hot", cacheManager = "caffeineCacheManager", key = "#topicId + ':' + #page + ':' + #size")
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
            // ??????
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
    public AbstractTypeOperateStatistics.TypeNumber like(Long discussId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        AbstractTypeOperateStatistics.TypeNumber typeNumber = discussLikeStatistics.insert(uid, discussId, AbstractTypeOperateStatistics.TypeEnum.POSITIVE);
        createMessageNotification(uid, nickname, discussId, MessageNotificationActionEnum.LIKE);
        return typeNumber;
    }

    @Override
    public AbstractTypeOperateStatistics.TypeNumber dislike(Long discussId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        AbstractTypeOperateStatistics.TypeNumber typeNumber = discussLikeStatistics.insert(uid, discussId, AbstractTypeOperateStatistics.TypeEnum.NEGATIVE);
        createMessageNotification(uid, nickname, discussId, MessageNotificationActionEnum.DISLIKE);
        return typeNumber;
    }

    public void createMessageNotification(Long uid, String nickname, Long discussId, MessageNotificationActionEnum action) {
        // ????????????id
        LambdaQueryWrapper<TopicDiscuss> queryWrapper = new QueryWrapper<TopicDiscuss>().lambda();
        TopicDiscuss topicDiscuss = baseMapper.selectOne(queryWrapper.select(TopicDiscuss::getTopicId).eq(TopicDiscuss::getDiscussId, discussId));
        Long topicId = topicDiscuss.getTopicId();
        if (!messageNotificationProducer.exists(uid, topicId, MessageNotificationMainTypeEnum.ARTICLE, discussId, MessageNotificationTargetTypeEnum.ARTICLE, action)) {
            messageNotificationProducer.create(uid, nickname, topicId, MessageNotificationMainTypeEnum.TOPIC, discussId, MessageNotificationTargetTypeEnum.DISCUSS, action);
            // ????????????
            if (action == MessageNotificationActionEnum.LIKE) {
                int increment = topicHotStatistics.updateIncrement(topicId, HeatDeltaEnum.DISCUSS_LIKE.getDelta());
                log.info("???????????????topicId???{},???????????????{}", topicId, increment);
                int increment1 = discussHotStatistics.updateIncrement(topicId, discussId, HeatDeltaEnum.DISCUSS_LIKE.getDelta());
                log.info("???????????????discussId???{},???????????????{}", discussId, increment1);
            } else if (action == MessageNotificationActionEnum.DISLIKE) {
                int increment = topicHotStatistics.updateIncrement(topicId, HeatDeltaEnum.DISCUSS_DISLIKE.getDelta());
                log.info("???????????????topicId???{},???????????????{}", topicId, increment);
                int increment1 = discussHotStatistics.updateIncrement(topicId, discussId, HeatDeltaEnum.DISCUSS_DISLIKE.getDelta());
                log.info("???????????????discussId???{},???????????????{}", discussId, increment1);
            }
        }
    }

    @Override
    public void remove(Long discussId) {
        long uid = tokenFeignService.getUid();
        LambdaQueryWrapper<TopicDiscuss> queryWrapper = new QueryWrapper<TopicDiscuss>().lambda();
        int delete = baseMapper.delete(queryWrapper.eq(TopicDiscuss::getDiscussId, discussId).eq(TopicDiscuss::getUid, uid));
        String message = "\n??????????????????,\ndiscussId=" + discussId + ",\nuid=" + uid;
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(message));
        // todo: rabbitmq????????????
        LambdaQueryWrapper<DiscussComment> discussCommentWrapper = new QueryWrapper<DiscussComment>().lambda();
        LambdaQueryWrapper<DiscussCommentChild> discussCommentChildWrapper = new QueryWrapper<DiscussCommentChild>().lambda();

        discussCommentMapper.delete(discussCommentWrapper.eq(DiscussComment::getDiscussId, discussId));
        discussCommentChildMapper.delete(discussCommentChildWrapper.eq(DiscussCommentChild::getDiscussId, discussId));
    }
}
