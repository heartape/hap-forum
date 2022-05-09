package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.business.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.business.entity.DiscussComment;
import com.heartape.hap.business.entity.DiscussCommentChild;
import com.heartape.hap.business.entity.bo.DiscussCommentChildBO;
import com.heartape.hap.business.entity.dto.DiscussCommentChildDTO;
import com.heartape.hap.business.exception.PermissionNoRemoveException;
import com.heartape.hap.business.exception.RelyDataNotExistedException;
import com.heartape.hap.business.exception.ResourceOperateRepeatException;
import com.heartape.hap.business.feign.HapUserDetails;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import com.heartape.hap.business.mapper.DiscussCommentChildMapper;
import com.heartape.hap.business.mapper.DiscussCommentMapper;
import com.heartape.hap.business.mq.producer.IMessageNotificationProducer;
import com.heartape.hap.business.service.IDiscussCommentChildService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.business.statistics.DiscussCommentChildLikeStatistics;
import com.heartape.hap.business.statistics.DiscussCommentLikeStatistics;
import com.heartape.hap.business.utils.AssertUtils;
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
public class DiscussCommentChildServiceImpl extends ServiceImpl<DiscussCommentChildMapper, DiscussCommentChild> implements IDiscussCommentChildService {

    @Autowired
    private DiscussCommentMapper discussCommentMapper;

    @Autowired
    private AssertUtils assertUtils;

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Autowired
    private DiscussCommentChildLikeStatistics discussCommentChildLikeStatistics;

    @Autowired
    private IMessageNotificationProducer messageNotificationProducer;

    @Override
    public void create(DiscussCommentChildDTO discussCommentChildDTO) {
        // 验证父评论是否存在
        Long topicId = discussCommentChildDTO.getTopicId();
        Long discussId = discussCommentChildDTO.getDiscussId();
        Long parentId = discussCommentChildDTO.getParentId();
        Long count = discussCommentMapper.selectCount(new QueryWrapper<DiscussComment>().eq("topic_id", topicId).eq("discuss_id", discussId).eq("comment_id", parentId));
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(String.format("DiscussCommentChild所依赖的DiscussComment:id=%s不存在", parentId)));

        DiscussCommentChild discussCommentChild = new DiscussCommentChild();
        BeanUtils.copyProperties(discussCommentChildDTO, discussCommentChild);
        discussCommentChild.setChildToChild(false);
        baseMapper.insert(discussCommentChild);
    }

    @Override
    public void createToChild(DiscussCommentChildDTO discussCommentChildDTO) {
        // 验证父评论是否存在
        Long topicId = discussCommentChildDTO.getTopicId();
        Long discussId = discussCommentChildDTO.getDiscussId();
        Long parentId = discussCommentChildDTO.getParentId();
        Long commentId = discussCommentChildDTO.getChildTarget();
        Long count = query().eq("topic_id", topicId).eq("comment_id", commentId).eq("discuss_id", discussId).eq("parent_id", parentId).count();
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(String.format("DiscussCommentChild所依赖的DiscussCommentChild:id=%s不存在", commentId)));

        DiscussCommentChild discussCommentChild = new DiscussCommentChild();
        BeanUtils.copyProperties(discussCommentChildDTO, discussCommentChild);
        discussCommentChild.setChildToChild(true);
        baseMapper.insert(discussCommentChild);
    }

    @Override
    public PageInfo<DiscussCommentChildBO> list(Long commentId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<DiscussCommentChild> children = query().eq("parent_id", commentId).list();
        PageInfo<DiscussCommentChild> childPageInfo = PageInfo.of(children);
        PageInfo<DiscussCommentChildBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(childPageInfo, boPageInfo);

        List<DiscussCommentChildBO> commentChildBOS = children.stream().map(discussCommentChild -> {
            DiscussCommentChildBO discussCommentChildBO = new DiscussCommentChildBO();
            BeanUtils.copyProperties(discussCommentChild, discussCommentChildBO);
            // 点赞
            Long childCommentId = discussCommentChild.getCommentId();
            int likeNumber = discussCommentChildLikeStatistics.getPositiveOperateNumber(childCommentId);
            int dislikeNumber = discussCommentChildLikeStatistics.getNegativeOperateNumber(childCommentId);
            discussCommentChildBO.setLike(likeNumber);
            discussCommentChildBO.setDislike(dislikeNumber);
            return discussCommentChildBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(commentChildBOS);
        return boPageInfo;
    }

    @Override
    public void like(Long commentId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        boolean b = discussCommentChildLikeStatistics.setPositiveOperate(commentId, uid);
        assertUtils.businessState(b, new ResourceOperateRepeatException("讨论子评论:" + commentId + ",用户:" + uid + "已经进行过点赞"));
        // 查询文章id
        LambdaQueryWrapper<DiscussCommentChild> queryWrapper = new QueryWrapper<DiscussCommentChild>().lambda();
        DiscussCommentChild discussCommentChild = baseMapper.selectOne(queryWrapper.select(DiscussCommentChild::getTopicId).eq(DiscussCommentChild::getCommentId, commentId));
        Long topicId = discussCommentChild.getTopicId();
        messageNotificationProducer.likeCreate(uid, nickname, topicId, MessageNotificationMainTypeEnum.TOPIC, commentId, MessageNotificationTargetTypeEnum.DISCUSS_COMMENT_CHILD);
    }

    @Override
    public void remove(Long commentId) {
        long uid = tokenFeignService.getUid();
        int delete = baseMapper.delete(new QueryWrapper<DiscussCommentChild>().eq("comment_id", commentId).eq("uid", uid));
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(String.format("没有DiscussCommentChild删除权限,commentId:%s,uid:%s", commentId, uid)));
    }
}
