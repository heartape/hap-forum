package com.heartape.hap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    private DiscussCommentHotStatistics discussCommentHotStatistics;

    @Autowired
    private DiscussCommentChildHotStatistics discussCommentChildHotStatistics;

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
        int hot = discussCommentHotStatistics.updateIncrement(discussId, commentId, DiscussCommentHotStatistics.INIT_HOT);
        log.info("discussId:" + discussId + ",commentId:" + commentId + ",设置初始热度为" + hot);
    }

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
            List<Long> childIds = discussCommentChildHotStatistics.selectPage(mainId, 1, 2)
                    .stream().map(AbstractCumulativeOperateStatistics.CumulativeValue::getResourceId).collect(Collectors.toList());
            LambdaQueryWrapper<DiscussCommentChild> queryWrapper = new QueryWrapper<DiscussCommentChild>().lambda();
            // todo:控制字段
            queryWrapper.in(DiscussCommentChild::getCommentId, childIds);
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
    public boolean like(Long commentId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        // todo:将TypeNumber封装并返回
        AbstractTypeOperateStatistics.TypeNumber typeNumber = discussCommentLikeStatistics.insert(commentId, uid, AbstractTypeOperateStatistics.TypeEnum.POSITIVE);
        if (true) {
            // 查询文章id
            LambdaQueryWrapper<DiscussComment> queryWrapper = new QueryWrapper<DiscussComment>().lambda();
            DiscussComment discussComment = baseMapper.selectOne(queryWrapper.select(DiscussComment::getTopicId).eq(DiscussComment::getCommentId, commentId));
            Long topicId = discussComment.getTopicId();
            messageNotificationProducer.likeCreate(uid, nickname, topicId, MessageNotificationMainTypeEnum.TOPIC, commentId, MessageNotificationTargetTypeEnum.DISCUSS_COMMENT);
        }
        return true;
    }

    @Override
    public boolean dislike(Long commentId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        // todo:将TypeNumber封装并返回
        discussCommentLikeStatistics.insert(commentId, uid, AbstractTypeOperateStatistics.TypeEnum.NEGATIVE);
        if (true) {
            // 查询文章id
            LambdaQueryWrapper<DiscussComment> queryWrapper = new QueryWrapper<DiscussComment>().lambda();
            DiscussComment discussComment = baseMapper.selectOne(queryWrapper.select(DiscussComment::getTopicId).eq(DiscussComment::getCommentId, commentId));
            Long topicId = discussComment.getTopicId();
            messageNotificationProducer.dislikeCreate(uid, nickname, topicId, MessageNotificationMainTypeEnum.TOPIC, commentId, MessageNotificationTargetTypeEnum.DISCUSS_COMMENT);
        }
        return true;
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
