package com.heartape.hap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.constant.HeatDeltaEnum;
import com.heartape.hap.constant.MessageNotificationActionEnum;
import com.heartape.hap.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.entity.DiscussComment;
import com.heartape.hap.entity.DiscussCommentChild;
import com.heartape.hap.entity.TopicDiscuss;
import com.heartape.hap.entity.bo.DiscussCommentBO;
import com.heartape.hap.entity.bo.DiscussCommentChildBO;
import com.heartape.hap.entity.dto.DiscussCommentDTO;
import com.heartape.hap.exception.PermissionNoRemoveException;
import com.heartape.hap.exception.RelyDataNotExistedException;
import com.heartape.hap.feign.HapUserDetails;
import com.heartape.hap.feign.TokenFeignServiceImpl;
import com.heartape.hap.mapper.DiscussCommentChildMapper;
import com.heartape.hap.mapper.DiscussCommentMapper;
import com.heartape.hap.mapper.TopicDiscussMapper;
import com.heartape.hap.mq.producer.IMessageNotificationProducer;
import com.heartape.hap.service.IDiscussCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.statistics.*;
import com.heartape.hap.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class DiscussCommentServiceImpl extends ServiceImpl<DiscussCommentMapper, DiscussComment> implements IDiscussCommentService {

    @Autowired
    private TopicDiscussMapper topicDiscussMapper;

    @Autowired
    private DiscussCommentChildMapper discussCommentChildMapper;

    @Autowired
    private AssertUtils assertUtils;

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Autowired
    private DiscussCommentLikeStatistics discussCommentLikeStatistics;

    @Autowired
    private DiscussCommentChildLikeStatistics discussCommentChildLikeStatistics;

    @Autowired
    private IMessageNotificationProducer messageNotificationProducer;

    @Autowired
    private TopicHotStatistics topicHotStatistics;

    @Autowired
    private DiscussHotStatistics discussHotStatistics;

    @Autowired
    private DiscussCommentHotStatistics discussCommentHotStatistics;

    @Autowired
    private DiscussCommentChildHotStatistics discussCommentChildHotStatistics;

    @Caching(evict = {
            @CacheEvict(value = "dc-hot", cacheManager = "caffeineCacheManager", key = "#discussCommentDTO.discussId + ':1:10'"),
            @CacheEvict(value = "dc-hot", cacheManager = "caffeineCacheManager", key = "#discussCommentDTO.discussId + ':2:10'")
    })
    @Override
    public void create(DiscussCommentDTO discussCommentDTO) {
        // 验证讨论是否存在
        Long topicId = discussCommentDTO.getTopicId();
        Long discussId = discussCommentDTO.getDiscussId();

        LambdaQueryWrapper<TopicDiscuss> queryWrapper = new QueryWrapper<TopicDiscuss>().lambda();
        Long count = topicDiscussMapper.selectCount(queryWrapper.eq(TopicDiscuss::getTopicId, topicId).eq(TopicDiscuss::getDiscussId, discussId));
        String message = "\nDiscussComment所依赖的TopicDiscuss不存在,\ntopicId=" + topicId +",\ndiscussId=" + discussId;
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(message));

        DiscussComment discussComment = new DiscussComment();
        BeanUtils.copyProperties(discussCommentDTO, discussComment);

        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String avatar = tokenInfo.getAvatar();
        String nickname = tokenInfo.getNickname();
        discussComment.setUid(uid);
        discussComment.setAvatar(avatar);
        discussComment.setNickname(nickname);
        baseMapper.insert(discussComment);
        // 初始化热度
        Long commentId = discussComment.getCommentId();
        int hot = discussCommentHotStatistics.updateIncrement(discussId, commentId, HeatDeltaEnum.DISCUSS_COMMENT_INIT.getDelta());
        log.info("discussId:{},commentId:{},设置初始热度为{}", discussId, commentId, hot);
        int increment = topicHotStatistics.updateIncrement(topicId, HeatDeltaEnum.DISCUSS_COMMENT_INIT.getDelta());
        log.info("热度增加，topicId：{},当前热度：{}", topicId, increment);
        int increment1 = discussHotStatistics.updateIncrement(topicId, discussId, HeatDeltaEnum.DISCUSS_COMMENT_INIT.getDelta());
        log.info("热度增加，discussId：{},当前热度：{}", discussId, increment1);
    }

    @Cacheable(value = "dc-hot", cacheManager = "caffeineCacheManager", key = "#discussId + ':' + #page + ':' + #size")
    @Override
    public PageInfo<DiscussCommentBO> list(Long discussId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<DiscussComment> discussComments = baseMapper.selectWithChildCount(discussId);
        PageInfo<DiscussComment> pageInfo = PageInfo.of(discussComments);
        PageInfo<DiscussCommentBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, boPageInfo);
        List<DiscussCommentBO> discussCommentBOS = discussComments.stream().map(discussComment -> {
            DiscussCommentBO discussCommentBO = new DiscussCommentBO();
            BeanUtils.copyProperties(discussComment, discussCommentBO);
            // 获取高热度评论
            Long mainId = discussComment.getCommentId();
            List<AbstractCumulativeOperateStatistics.CumulativeValue> cumulativeValues = discussCommentChildHotStatistics.selectPage(mainId, 1, 2);
            LambdaQueryWrapper<DiscussCommentChild> queryWrapper = new QueryWrapper<DiscussCommentChild>().lambda();
            // todo:控制查询字段
            if (CollectionUtils.isEmpty(cumulativeValues)) {
                queryWrapper.eq(DiscussCommentChild::getParentId, mainId);
            } else {
                List<Long> childIds = cumulativeValues.stream().map(AbstractCumulativeOperateStatistics.CumulativeValue::getResourceId).collect(Collectors.toList());
                queryWrapper.in(DiscussCommentChild::getCommentId, childIds);
            }
            PageHelper.startPage(1, 2);
            List<DiscussCommentChild> discussCommentChildren = discussCommentChildMapper.selectList(queryWrapper);
            // 转换为bo
            List<DiscussCommentChildBO> commentChildBOS = discussCommentChildren.stream().map(discussCommentChild -> {
                DiscussCommentChildBO discussCommentChildBO = new DiscussCommentChildBO();
                BeanUtils.copyProperties(discussCommentChild, discussCommentChildBO);
                // 点赞
                Long childCommentId = discussCommentChild.getCommentId();
                int likeNumber = discussCommentChildLikeStatistics.selectPositiveNumber(childCommentId);
                int dislikeNumber = discussCommentChildLikeStatistics.selectNegativeNumber(childCommentId);
                discussCommentChildBO.setLike(likeNumber);
                discussCommentChildBO.setDislike(dislikeNumber);
                return discussCommentChildBO;
            }).collect(Collectors.toList());
            discussCommentBO.setSimpleChildren(commentChildBOS);
            // 点赞
            Long commentId = discussComment.getCommentId();
            int likeNumber = discussCommentLikeStatistics.selectPositiveNumber(commentId);
            int dislikeNumber = discussCommentLikeStatistics.selectNegativeNumber(commentId);
            discussCommentBO.setLike(likeNumber);
            discussCommentBO.setDislike(dislikeNumber);
            return discussCommentBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(discussCommentBOS);
        return boPageInfo;
    }

    @Override
    public AbstractTypeOperateStatistics.TypeNumber like(Long commentId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        AbstractTypeOperateStatistics.TypeNumber typeNumber = discussCommentLikeStatistics.insert(uid, commentId, AbstractTypeOperateStatistics.TypeEnum.POSITIVE);
        createMessageNotification(uid, nickname, commentId, MessageNotificationActionEnum.LIKE);
        return typeNumber;
    }

    @Override
    public AbstractTypeOperateStatistics.TypeNumber dislike(Long commentId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        AbstractTypeOperateStatistics.TypeNumber typeNumber = discussCommentLikeStatistics.insert(uid, commentId, AbstractTypeOperateStatistics.TypeEnum.NEGATIVE);
        createMessageNotification(uid, nickname, commentId, MessageNotificationActionEnum.DISLIKE);
        return typeNumber;
    }

    @Async
    public void createMessageNotification(Long uid, String nickname, Long commentId, MessageNotificationActionEnum action) {
        // 查询话题id
        LambdaQueryWrapper<DiscussComment> queryWrapper = new QueryWrapper<DiscussComment>().lambda();
        DiscussComment discussComment = baseMapper.selectOne(queryWrapper.select(DiscussComment::getTopicId, DiscussComment::getDiscussId).eq(DiscussComment::getCommentId, commentId));
        Long topicId = discussComment.getTopicId();
        Long discussId = discussComment.getDiscussId();
        if (!messageNotificationProducer.exists(uid, topicId, MessageNotificationMainTypeEnum.TOPIC, commentId, MessageNotificationTargetTypeEnum.DISCUSS_COMMENT, action)) {
            messageNotificationProducer.create(uid, nickname, topicId, MessageNotificationMainTypeEnum.TOPIC, commentId, MessageNotificationTargetTypeEnum.DISCUSS_COMMENT, action);
            // 增加热度
            if (action == MessageNotificationActionEnum.LIKE) {
                int increment = topicHotStatistics.updateIncrement(topicId, HeatDeltaEnum.DISCUSS_COMMENT_LIKE.getDelta());
                log.info("热度增加，topicId：{},当前热度：{}", topicId, increment);
                int increment1 = discussHotStatistics.updateIncrement(topicId, discussId, HeatDeltaEnum.DISCUSS_COMMENT_LIKE.getDelta());
                log.info("热度增加，discussId：{},当前热度：{}", discussId, increment1);
                int increment2 = discussCommentHotStatistics.updateIncrement(discussId, commentId, HeatDeltaEnum.DISCUSS_COMMENT_LIKE.getDelta());
                log.info("热度增加，commentId：{},当前热度：{}", commentId, increment2);
            } else if (action == MessageNotificationActionEnum.DISLIKE) {
                int increment = topicHotStatistics.updateIncrement(topicId, HeatDeltaEnum.DISCUSS_COMMENT_DISLIKE.getDelta());
                log.info("热度增加，topicId：{},当前热度：{}", topicId, increment);
                int increment1 = discussHotStatistics.updateIncrement(topicId, discussId, HeatDeltaEnum.DISCUSS_COMMENT_DISLIKE.getDelta());
                log.info("热度增加，discussId：{},当前热度：{}", discussId, increment1);
                int increment2 = discussCommentHotStatistics.updateIncrement(discussId, commentId, HeatDeltaEnum.DISCUSS_COMMENT_DISLIKE.getDelta());
                log.info("热度增加，commentId：{},当前热度：{}", commentId, increment2);
            }
        }
    }

    @Override
    public void remove(Long commentId) {
        long uid = tokenFeignService.getUid();

        LambdaQueryWrapper<DiscussComment> discussCommentWrapper = new QueryWrapper<DiscussComment>().lambda();
        int delete = baseMapper.delete(discussCommentWrapper.eq(DiscussComment::getCommentId, commentId).eq(DiscussComment::getUid, uid));

        String message = "\n没有DiscussComment删除权限,\ncommentId=" + commentId + ",\nuid=" + uid;
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(message));
        // todo:异步删除，同时删除热度和点赞数据
        LambdaQueryWrapper<DiscussCommentChild> discussCommentChildWrapper = new QueryWrapper<DiscussCommentChild>().lambda();
        discussCommentChildMapper.delete(discussCommentChildWrapper.eq(DiscussCommentChild::getParentId, commentId));
    }
}
