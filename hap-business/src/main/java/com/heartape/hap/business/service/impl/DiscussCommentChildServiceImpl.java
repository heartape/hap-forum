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

        LambdaQueryWrapper<DiscussComment> queryWrapper = new QueryWrapper<DiscussComment>().lambda();
        Long count = discussCommentMapper.selectCount(queryWrapper.eq(DiscussComment::getTopicId, topicId).eq(DiscussComment::getDiscussId, discussId).eq(DiscussComment::getCommentId, parentId));
        String message = "\nDiscussCommentChild所依赖的DiscussComment不存在,\ntopicId=" + topicId + ",\ndiscussId=" + discussId + ",\nparentId=" + parentId;
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(message));

        DiscussCommentChild discussCommentChild = new DiscussCommentChild();
        BeanUtils.copyProperties(discussCommentChildDTO, discussCommentChild);
        discussCommentChild.setChildToChild(false);

        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String avatar = tokenInfo.getAvatar();
        String nickname = tokenInfo.getNickname();
        discussCommentChild.setUid(uid);
        discussCommentChild.setAvatar(avatar);
        discussCommentChild.setNickname(nickname);
        baseMapper.insert(discussCommentChild);
    }

    @Override
    public void createToChild(DiscussCommentChildDTO discussCommentChildDTO) {
        // 验证父评论是否存在
        Long topicId = discussCommentChildDTO.getTopicId();
        Long discussId = discussCommentChildDTO.getDiscussId();
        Long parentId = discussCommentChildDTO.getParentId();
        Long childTarget = discussCommentChildDTO.getChildTarget();
        LambdaQueryWrapper<DiscussCommentChild> queryWrapper = new QueryWrapper<DiscussCommentChild>().lambda();
        Long count = baseMapper.selectCount(queryWrapper.eq(DiscussCommentChild::getTopicId, topicId).eq(DiscussCommentChild::getDiscussId, discussId)
                .eq(DiscussCommentChild::getParentId, parentId).eq(DiscussCommentChild::getUid, childTarget));
        String message = "\nDiscussCommentChild所依赖的DiscussCommentChild不存在,\ntopic_id=" + topicId + ",\ndiscuss_id=" + discussId + ",\nparent_id=" + parentId + ",\nuid=" + childTarget;
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(message));

        DiscussCommentChild discussCommentChild = new DiscussCommentChild();
        BeanUtils.copyProperties(discussCommentChildDTO, discussCommentChild);
        discussCommentChild.setChildToChild(true);
        // todo:考虑是否需要将两次请求打包
        String childTargetName = tokenFeignService.getNickname(childTarget);
        discussCommentChild.setChildTargetName(childTargetName);

        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String avatar = tokenInfo.getAvatar();
        String nickname = tokenInfo.getNickname();
        discussCommentChild.setUid(uid);
        discussCommentChild.setAvatar(avatar);
        discussCommentChild.setNickname(nickname);
        baseMapper.insert(discussCommentChild);
    }

    @Override
    public PageInfo<DiscussCommentChildBO> list(Long commentId, Integer page, Integer size) {
        LambdaQueryWrapper<DiscussCommentChild> queryWrapper = new QueryWrapper<DiscussCommentChild>().lambda();
        PageHelper.startPage(page, size);
        List<DiscussCommentChild> children = baseMapper.selectList(queryWrapper.eq(DiscussCommentChild::getParentId, commentId));
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
    public void dislike(Long commentId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        boolean b = discussCommentChildLikeStatistics.setPositiveOperate(commentId, uid);
        assertUtils.businessState(b, new ResourceOperateRepeatException("讨论子评论:" + commentId + ",用户:" + uid + "已经进行过点踩"));
        // 查询文章id
        LambdaQueryWrapper<DiscussCommentChild> queryWrapper = new QueryWrapper<DiscussCommentChild>().lambda();
        DiscussCommentChild discussCommentChild = baseMapper.selectOne(queryWrapper.select(DiscussCommentChild::getTopicId).eq(DiscussCommentChild::getCommentId, commentId));
        Long topicId = discussCommentChild.getTopicId();
        messageNotificationProducer.dislikeCreate(uid, nickname, topicId, MessageNotificationMainTypeEnum.TOPIC, commentId, MessageNotificationTargetTypeEnum.DISCUSS_COMMENT_CHILD);
    }

    @Override
    public void remove(Long commentId) {
        long uid = tokenFeignService.getUid();
        LambdaQueryWrapper<DiscussCommentChild> queryWrapper = new QueryWrapper<DiscussCommentChild>().lambda();
        int delete = baseMapper.delete(queryWrapper.eq(DiscussCommentChild::getCommentId, commentId).eq(DiscussCommentChild::getUid, uid));
        String message = "\n没有DiscussCommentChild删除权限,\ncommentId=" + commentId +",\nuid=" + uid;
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(message));
    }
}
