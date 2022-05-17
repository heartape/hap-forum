package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.business.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.business.entity.ArticleComment;
import com.heartape.hap.business.entity.ArticleCommentChild;
import com.heartape.hap.business.entity.bo.ArticleCommentChildBO;
import com.heartape.hap.business.entity.dto.ArticleCommentChildDTO;
import com.heartape.hap.business.exception.PermissionNoRemoveException;
import com.heartape.hap.business.exception.RelyDataNotExistedException;
import com.heartape.hap.business.feign.HapUserDetails;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import com.heartape.hap.business.mapper.ArticleCommentChildMapper;
import com.heartape.hap.business.mapper.ArticleCommentMapper;
import com.heartape.hap.business.mq.producer.IMessageNotificationProducer;
import com.heartape.hap.business.service.IArticleCommentChildService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.business.statistics.ArticleCommentChildHotStatistics;
import com.heartape.hap.business.statistics.ArticleCommentChildLikeStatistics;
import com.heartape.hap.business.statistics.ArticleHotStatistics;
import com.heartape.hap.business.utils.AssertUtils;
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
public class ArticleCommentChildServiceImpl extends ServiceImpl<ArticleCommentChildMapper, ArticleCommentChild> implements IArticleCommentChildService {

    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    @Autowired
    private ArticleCommentChildLikeStatistics articleCommentChildLikeStatistics;

    @Autowired
    private ArticleCommentChildHotStatistics articleCommentChildHotStatistics;

    @Autowired
    private AssertUtils assertUtils;

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Autowired
    private IMessageNotificationProducer messageNotificationProducer;

    @Override
    public void create(ArticleCommentChildDTO articleCommentChildDTO) {

        Long articleId = articleCommentChildDTO.getArticleId();
        Long parentId = articleCommentChildDTO.getParentId();

        LambdaQueryWrapper<ArticleComment> queryWrapper = new QueryWrapper<ArticleComment>().lambda();
        Long count = articleCommentMapper.selectCount(queryWrapper.eq(ArticleComment::getArticleId, articleId).eq(ArticleComment::getCommentId, parentId));
        String message = "\nArticleCommentChild所依赖的ArticleComment不存在,\narticleId=" + articleId + ",\nparentId=" + parentId;
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(message));

        ArticleCommentChild articleCommentChild = new ArticleCommentChild();
        BeanUtils.copyProperties(articleCommentChildDTO, articleCommentChild);
        articleCommentChild.setChildToChild(false);

        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String avatar = tokenInfo.getAvatar();
        String nickname = tokenInfo.getNickname();
        articleCommentChild.setUid(uid);
        articleCommentChild.setAvatar(avatar);
        articleCommentChild.setNickname(nickname);
        baseMapper.insert(articleCommentChild);
        // 初始化热度
        Long commentId = articleCommentChild.getCommentId();
        int hot = articleCommentChildHotStatistics.operateIncrement(parentId, commentId, ArticleHotStatistics.INIT_HOT);
        log.info("parentId:" + parentId + ",commentId:" + commentId + ",设置初始热度为" + hot);
    }

    @Override
    public void createToChild(ArticleCommentChildDTO articleCommentChildDTO) {
        Long articleId = articleCommentChildDTO.getArticleId();
        Long parentId = articleCommentChildDTO.getParentId();
        Long childTarget = articleCommentChildDTO.getChildTarget();

        LambdaQueryWrapper<ArticleCommentChild> queryWrapper = new QueryWrapper<ArticleCommentChild>().lambda();
        Long count = baseMapper.selectCount(queryWrapper.eq(ArticleCommentChild::getArticleId, articleId)
                .eq(ArticleCommentChild::getParentId, parentId).eq(ArticleCommentChild::getUid, childTarget));
        assertUtils.businessState(count == 1, new RelyDataNotExistedException("ArticleCommentChild所依赖的ArticleCommentChild不存在"));

        ArticleCommentChild articleCommentChild = new ArticleCommentChild();
        BeanUtils.copyProperties(articleCommentChildDTO, articleCommentChild);
        articleCommentChild.setChildToChild(true);
        // 远程调用获取childTargetName
        String childTargetName = tokenFeignService.getNickname(childTarget);
        articleCommentChild.setChildTargetName(childTargetName);

        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String avatar = tokenInfo.getAvatar();
        String nickname = tokenInfo.getNickname();
        articleCommentChild.setUid(uid);
        articleCommentChild.setAvatar(avatar);
        articleCommentChild.setNickname(nickname);
        baseMapper.insert(articleCommentChild);
        // 初始化热度
        Long commentId = articleCommentChild.getCommentId();
        int hot = articleCommentChildHotStatistics.operateIncrement(parentId, commentId, ArticleHotStatistics.INIT_HOT);
        log.info("parentId:" + parentId + ",commentId:" + commentId + ",设置初始热度为" + hot);
    }

    @Override
    public PageInfo<ArticleCommentChildBO> list(Long commentId, Integer page, Integer size) {
        LambdaQueryWrapper<ArticleCommentChild> queryWrapper = new QueryWrapper<ArticleCommentChild>().lambda();
        PageHelper.startPage(page, size);
        List<ArticleCommentChild> commentChildren = baseMapper.selectList(queryWrapper.select(ArticleCommentChild::getCommentId,
                ArticleCommentChild::getUid,ArticleCommentChild::getAvatar,ArticleCommentChild::getNickname, ArticleCommentChild::getChildToChild,
                ArticleCommentChild::getChildTarget,ArticleCommentChild::getChildTargetName,ArticleCommentChild::getContent,ArticleCommentChild::getCreatedTime)
                .eq(ArticleCommentChild::getParentId, commentId));

        PageInfo<ArticleCommentChild> childPageInfo = PageInfo.of(commentChildren);
        List<ArticleCommentChildBO> childBOList = commentChildren.stream().map(child -> {
            ArticleCommentChildBO childBO = new ArticleCommentChildBO();
            BeanUtils.copyProperties(child, childBO);
            // 点赞
            Long childCommentId = child.getCommentId();
            int likeNumber = articleCommentChildLikeStatistics.getPositiveOperateNumber(childCommentId);
            int dislikeNumber = articleCommentChildLikeStatistics.getNegativeOperateNumber(childCommentId);
            childBO.setLike(likeNumber);
            childBO.setDislike(dislikeNumber);
            return childBO;
        }).collect(Collectors.toList());
        PageInfo<ArticleCommentChildBO> childBOPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(childPageInfo, childBOPageInfo);
        childBOPageInfo.setList(childBOList);
        return childBOPageInfo;
    }

    @Override
    public boolean like(Long commentId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        boolean positiveOperate = articleCommentChildLikeStatistics.setPositiveOperate(commentId, uid);
        if (positiveOperate) {
            // 查询文章id
            LambdaQueryWrapper<ArticleCommentChild> queryWrapper = new QueryWrapper<ArticleCommentChild>().lambda();
            ArticleCommentChild articleCommentChild = baseMapper.selectOne(queryWrapper.select(ArticleCommentChild::getArticleId).eq(ArticleCommentChild::getCommentId, commentId));
            Long articleId = articleCommentChild.getArticleId();
            messageNotificationProducer.likeCreate(uid, nickname, articleId, MessageNotificationMainTypeEnum.ARTICLE, commentId, MessageNotificationTargetTypeEnum.ARTICLE_COMMENT_CHILD);
        }
        return positiveOperate;
    }

    @Override
    public boolean dislike(Long commentId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        boolean negativeOperate = articleCommentChildLikeStatistics.setNegativeOperate(commentId, uid);
        if (negativeOperate) {
            // 查询文章id
            LambdaQueryWrapper<ArticleCommentChild> queryWrapper = new QueryWrapper<ArticleCommentChild>().lambda();
            ArticleCommentChild articleCommentChild = baseMapper.selectOne(queryWrapper.select(ArticleCommentChild::getArticleId).eq(ArticleCommentChild::getCommentId, commentId));
            Long articleId = articleCommentChild.getArticleId();
            messageNotificationProducer.dislikeCreate(uid, nickname, articleId, MessageNotificationMainTypeEnum.ARTICLE, commentId, MessageNotificationTargetTypeEnum.ARTICLE_COMMENT_CHILD);
        }
        return negativeOperate;
    }

    @Override
    public void remove(Long commentId) {
        long uid = tokenFeignService.getUid();
        LambdaQueryWrapper<ArticleCommentChild> queryWrapper = new QueryWrapper<ArticleCommentChild>().lambda();
        int delete = baseMapper.delete(queryWrapper.eq(ArticleCommentChild::getCommentId, commentId).eq(ArticleCommentChild::getUid, uid));
        String message = "\n没有ArticleCommentChild删除权限,\ncommentId=" + commentId + ",\nuid=" + uid;
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(message));
        // todo:删除点赞数据
    }
}
