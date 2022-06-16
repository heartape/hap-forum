package com.heartape.hap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.constant.HeatDeltaEnum;
import com.heartape.hap.constant.MessageNotificationActionEnum;
import com.heartape.hap.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.entity.ArticleComment;
import com.heartape.hap.entity.ArticleCommentChild;
import com.heartape.hap.entity.bo.ArticleCommentChildBO;
import com.heartape.hap.entity.dto.ArticleCommentChildDTO;
import com.heartape.hap.exception.PermissionNoRemoveException;
import com.heartape.hap.exception.RelyDataNotExistedException;
import com.heartape.hap.feign.HapUserDetails;
import com.heartape.hap.feign.TokenFeignServiceImpl;
import com.heartape.hap.mapper.ArticleCommentChildMapper;
import com.heartape.hap.mapper.ArticleCommentMapper;
import com.heartape.hap.mq.producer.IMessageNotificationProducer;
import com.heartape.hap.service.IArticleCommentChildService;
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
    private ArticleHotStatistics articleHotStatistics;

    @Autowired
    private ArticleCommentHotStatistics articleCommentHotStatistics;

    @Autowired
    private ArticleCommentChildHotStatistics articleCommentChildHotStatistics;

    @Autowired
    private AssertUtils assertUtils;

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Autowired
    private IMessageNotificationProducer messageNotificationProducer;

    @Caching(evict = {
            @CacheEvict(value = "acc-hot", cacheManager = "caffeineCacheManager", key = "#articleCommentChildDTO.articleId + ':1:10'"),
            @CacheEvict(value = "acc-hot", cacheManager = "caffeineCacheManager", key = "#articleCommentChildDTO.articleId + ':2:10'")
    })
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
        articleHotStatistics.updateIncrement(articleId, HeatDeltaEnum.ARTICLE_COMMENT_CHILD_LIKE.getDelta());
        articleCommentHotStatistics.updateIncrement(articleId, parentId, HeatDeltaEnum.ARTICLE_COMMENT_CHILD_LIKE.getDelta());
        int hot = articleCommentChildHotStatistics.updateIncrement(parentId, commentId, HeatDeltaEnum.ARTICLE_INIT.getDelta());
        log.info("parentId:{},commentId:{},设置初始热度为:{}", parentId, commentId, hot);
    }

    @Override
    public void createToChild(ArticleCommentChildDTO articleCommentChildDTO) {
        Long articleId = articleCommentChildDTO.getArticleId();
        Long parentId = articleCommentChildDTO.getParentId();
        Long childTarget = articleCommentChildDTO.getChildTarget();

        LambdaQueryWrapper<ArticleCommentChild> queryWrapper = new QueryWrapper<ArticleCommentChild>().lambda();
        Long count = baseMapper.selectCount(queryWrapper.eq(ArticleCommentChild::getArticleId, articleId)
                .eq(ArticleCommentChild::getParentId, parentId).eq(ArticleCommentChild::getUid, childTarget));
        String message = "\nArticleCommentChild所依赖的ArticleComment不存在,\narticleId=" + articleId + ",\nparentId=" + parentId;
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(message));

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
        int i = articleHotStatistics.updateIncrement(articleId, HeatDeltaEnum.ARTICLE_COMMENT_CHILD_INIT.getDelta());
        log.info("articleId:{},热度为:{}", articleId, i);
        int i1 = articleCommentHotStatistics.updateIncrement(articleId, parentId, HeatDeltaEnum.ARTICLE_COMMENT_CHILD_INIT.getDelta());
        log.info("parentId:{},热度为:{}", parentId, i1);
        int hot = articleCommentChildHotStatistics.updateIncrement(parentId, commentId, HeatDeltaEnum.ARTICLE_COMMENT_CHILD_INIT.getDelta());
        log.info("commentId:{},设置初始热度为:{}", commentId, hot);
    }

    @Cacheable(value = "acc-hot", cacheManager = "caffeineCacheManager", key = "#commentId + ':' + #page + ':' + #size")
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
            int likeNumber = articleCommentChildLikeStatistics.selectPositiveNumber(childCommentId);
            int dislikeNumber = articleCommentChildLikeStatistics.selectNegativeNumber(childCommentId);
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
    public AbstractTypeOperateStatistics.TypeNumber like(Long commentId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        AbstractTypeOperateStatistics.TypeNumber typeNumber = articleCommentChildLikeStatistics.insert(uid, commentId, AbstractTypeOperateStatistics.TypeEnum.POSITIVE);
        createMessageNotification(uid, nickname, commentId, MessageNotificationActionEnum.LIKE);
        return typeNumber;
    }

    @Override
    public AbstractTypeOperateStatistics.TypeNumber dislike(Long commentId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        AbstractTypeOperateStatistics.TypeNumber typeNumber = articleCommentChildLikeStatistics.insert(uid, commentId, AbstractTypeOperateStatistics.TypeEnum.NEGATIVE);
        createMessageNotification(uid, nickname, commentId, MessageNotificationActionEnum.DISLIKE);
        return typeNumber;
    }

    @Async
    public void createMessageNotification(Long uid, String nickname, Long commentId, MessageNotificationActionEnum action) {
        // 查询文章id
        LambdaQueryWrapper<ArticleCommentChild> queryWrapper = new QueryWrapper<ArticleCommentChild>().lambda();
        ArticleCommentChild articleCommentChild = baseMapper.selectOne(queryWrapper.select(ArticleCommentChild::getArticleId, ArticleCommentChild::getParentId).eq(ArticleCommentChild::getCommentId, commentId));
        Long articleId = articleCommentChild.getArticleId();
        Long parentId = articleCommentChild.getParentId();
        if (!messageNotificationProducer.exists(uid, articleId, MessageNotificationMainTypeEnum.ARTICLE, commentId, MessageNotificationTargetTypeEnum.ARTICLE_COMMENT_CHILD, action)) {
            messageNotificationProducer.create(uid, nickname, articleId, MessageNotificationMainTypeEnum.ARTICLE, commentId, MessageNotificationTargetTypeEnum.ARTICLE_COMMENT_CHILD, action);
            // 增加热度
            if (action == MessageNotificationActionEnum.LIKE) {
                articleHotStatistics.updateIncrement(articleId, HeatDeltaEnum.ARTICLE_COMMENT_CHILD_LIKE.getDelta());
                articleCommentHotStatistics.updateIncrement(articleId, parentId, HeatDeltaEnum.ARTICLE_COMMENT_CHILD_LIKE.getDelta());
                articleCommentChildHotStatistics.updateIncrement(parentId, commentId, HeatDeltaEnum.ARTICLE_COMMENT_CHILD_LIKE.getDelta());
            } else if (action == MessageNotificationActionEnum.DISLIKE) {
                articleHotStatistics.updateIncrement(articleId, HeatDeltaEnum.ARTICLE_COMMENT_CHILD_DISLIKE.getDelta());
                articleCommentHotStatistics.updateIncrement(articleId, parentId, HeatDeltaEnum.ARTICLE_COMMENT_CHILD_DISLIKE.getDelta());
                articleCommentChildHotStatistics.updateIncrement(parentId, commentId, HeatDeltaEnum.ARTICLE_COMMENT_CHILD_DISLIKE.getDelta());
            }
        }
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
