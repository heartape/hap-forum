package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.business.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.business.entity.ArticleComment;
import com.heartape.hap.business.entity.DiscussComment;
import com.heartape.hap.business.entity.DiscussCommentChild;
import com.heartape.hap.business.entity.TopicDiscuss;
import com.heartape.hap.business.entity.bo.DiscussCommentBO;
import com.heartape.hap.business.entity.dto.DiscussCommentDTO;
import com.heartape.hap.business.exception.PermissionNoRemoveException;
import com.heartape.hap.business.exception.RelyDataNotExistedException;
import com.heartape.hap.business.exception.ResourceOperateRepeatException;
import com.heartape.hap.business.feign.HapUserDetails;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import com.heartape.hap.business.mapper.DiscussCommentChildMapper;
import com.heartape.hap.business.mapper.DiscussCommentMapper;
import com.heartape.hap.business.mapper.TopicDiscussMapper;
import com.heartape.hap.business.mq.producer.IMessageNotificationProducer;
import com.heartape.hap.business.service.IDiscussCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.business.statistics.DiscussCommentLikeStatistics;
import com.heartape.hap.business.utils.AssertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private IMessageNotificationProducer messageNotificationProducer;

    @Override
    public void create(DiscussCommentDTO discussCommentDTO) {
        // 验证讨论是否存在
        Long topicId = discussCommentDTO.getTopicId();
        Long discussId = discussCommentDTO.getDiscussId();
        Long count = topicDiscussMapper.selectCount(new QueryWrapper<TopicDiscuss>().eq("topic_id", topicId).eq("discuss_id", discussId));
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(String.format("TopicDiscuss所依赖的Topic:id=%s或Discuss:id=%s不存在", topicId, discussId)));

        DiscussComment discussComment = new DiscussComment();
        BeanUtils.copyProperties(discussCommentDTO, discussComment);
        baseMapper.insert(discussComment);
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
            // todo: 获取高热度评论
            discussCommentBO.setSimpleChildren(new ArrayList<>());
            // 点赞
            Long commentId = discussComment.getCommentId();
            int likeNumber = discussCommentLikeStatistics.getPositiveOperateNumber(commentId);
            int dislikeNumber = discussCommentLikeStatistics.getNegativeOperateNumber(commentId);
            discussCommentBO.setLike(likeNumber);
            discussCommentBO.setDislike(dislikeNumber);
            return discussCommentBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(discussCommentBOS);
        return boPageInfo;
    }

    @Override
    public void like(Long commentId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        boolean b = discussCommentLikeStatistics.setPositiveOperate(commentId, uid);
        assertUtils.businessState(b, new ResourceOperateRepeatException("讨论评论:" + commentId + ",用户:" + uid + "已经进行过点赞"));
        // 查询文章id
        LambdaQueryWrapper<DiscussComment> queryWrapper = new QueryWrapper<DiscussComment>().lambda();
        DiscussComment discussComment = baseMapper.selectOne(queryWrapper.select(DiscussComment::getTopicId).eq(DiscussComment::getCommentId, commentId));
        Long topicId = discussComment.getTopicId();
        messageNotificationProducer.likeCreate(uid, nickname, topicId, MessageNotificationMainTypeEnum.TOPIC, commentId, MessageNotificationTargetTypeEnum.DISCUSS_COMMENT);
    }

    @Override
    public void remove(Long commentId) {
        long uid = tokenFeignService.getUid();
        int delete = baseMapper.delete(new QueryWrapper<DiscussComment>().eq("comment_id", commentId).eq("uid", uid));
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(String.format("没有DiscussComment删除权限,commentId:%s,uid:%s", commentId, uid)));
        discussCommentChildMapper.delete(new QueryWrapper<DiscussCommentChild>().eq("parent_id", commentId));
    }
}
