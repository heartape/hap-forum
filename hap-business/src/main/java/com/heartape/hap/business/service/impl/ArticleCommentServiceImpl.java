package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.business.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.business.entity.Article;
import com.heartape.hap.business.entity.ArticleComment;
import com.heartape.hap.business.entity.ArticleCommentChild;
import com.heartape.hap.business.entity.bo.ArticleCommentBO;
import com.heartape.hap.business.entity.bo.ArticleCommentChildBO;
import com.heartape.hap.business.entity.dto.ArticleCommentDTO;
import com.heartape.hap.business.exception.PermissionNoRemoveException;
import com.heartape.hap.business.exception.RelyDataNotExistedException;
import com.heartape.hap.business.feign.HapUserDetails;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import com.heartape.hap.business.mapper.ArticleCommentChildMapper;
import com.heartape.hap.business.mapper.ArticleCommentMapper;
import com.heartape.hap.business.mapper.ArticleMapper;
import com.heartape.hap.business.mq.producer.IMessageNotificationProducer;
import com.heartape.hap.business.service.IArticleCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.business.statistics.*;
import com.heartape.hap.business.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
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
@Slf4j
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements IArticleCommentService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleCommentChildMapper articleCommentChildMapper;

    @Autowired
    private AssertUtils assertUtils;

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Autowired
    private IMessageNotificationProducer messageNotificationProducer;

    @Autowired
    private ArticleCommentLikeStatistics articleCommentLikeStatistics;

    @Autowired
    private ArticleCommentChildHotStatistics articleCommentChildHotStatistics;

    @Autowired
    private ArticleCommentChildLikeStatistics articleCommentChildLikeStatistics;

    @Autowired
    private ArticleCommentHotStatistics articleCommentHotStatistics;

    @Override
    public void create(ArticleCommentDTO articleCommentDTO) {
        Long articleId = articleCommentDTO.getArticleId();
        LambdaQueryWrapper<Article> queryWrapper = new QueryWrapper<Article>().lambda();
        Long count = articleMapper.selectCount(queryWrapper.eq(Article::getArticleId, articleId));
        String message = "\nArticleComment所依赖的Article不存在:\narticleId=" + articleId;
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(message));

        ArticleComment articleComment = new ArticleComment();
        BeanUtils.copyProperties(articleCommentDTO, articleComment);

        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String avatar = tokenInfo.getAvatar();
        String nickname = tokenInfo.getNickname();
        articleComment.setUid(uid);
        articleComment.setAvatar(avatar);
        articleComment.setNickname(nickname);
        this.baseMapper.insert(articleComment);
        Long commentId = articleComment.getCommentId();
        // 初始化热度
        int hot = articleCommentHotStatistics.operateIncrement(articleId, commentId, ArticleHotStatistics.INIT_HOT);
        log.info("articleId:" + articleId + ",commentId:" + commentId + ",设置初始热度为" + hot);
    }

    @Override
    public PageInfo<ArticleCommentBO> list(Long articleId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<ArticleComment> articleComments = baseMapper.selectWithChildCount(articleId);
        PageInfo<ArticleComment> pageInfo = PageInfo.of(articleComments);
        PageInfo<ArticleCommentBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, boPageInfo);
        List<ArticleCommentBO> commentBOs = articleComments.stream().map(comment -> {
            ArticleCommentBO articleCommentBO = new ArticleCommentBO();
            BeanUtils.copyProperties(comment, articleCommentBO);
            // 从热度统计中获取较高的子评论
            Long mainId = comment.getCommentId();
            List<Long> commentIds = articleCommentChildHotStatistics.operateNumberPage(mainId, 1, 2)
                    .stream().map(ZSetOperations.TypedTuple::getValue).collect(Collectors.toList());
            LambdaQueryWrapper<ArticleCommentChild> queryWrapper = new QueryWrapper<ArticleCommentChild>().lambda();
            queryWrapper.select(ArticleCommentChild::getCommentId,
                    ArticleCommentChild::getUid,ArticleCommentChild::getAvatar,ArticleCommentChild::getNickname, ArticleCommentChild::getChildToChild,
                    ArticleCommentChild::getChildTarget,ArticleCommentChild::getChildTargetName,ArticleCommentChild::getContent,ArticleCommentChild::getCreatedTime)
                    .in(ArticleCommentChild::getCommentId, commentIds);
            List<ArticleCommentChild> commentChildren = articleCommentChildMapper.selectList(queryWrapper);
            // ArticleCommentChild转化为ArticleCommentChildBO
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
            articleCommentBO.setSimpleChildren(childBOList);
            // 点赞
            int likeNumber = articleCommentLikeStatistics.getPositiveOperateNumber(comment.getCommentId());
            int dislikeNumber = articleCommentLikeStatistics.getNegativeOperateNumber(comment.getCommentId());
            articleCommentBO.setLike(likeNumber);
            articleCommentBO.setDislike(dislikeNumber);
            return articleCommentBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(commentBOs);
        return boPageInfo;
    }

    @Override
    public boolean like(Long commentId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        boolean positiveOperate = articleCommentLikeStatistics.setPositiveOperate(commentId, uid);
        if (positiveOperate) {
            // 查询文章id
            LambdaQueryWrapper<ArticleComment> queryWrapper = new QueryWrapper<ArticleComment>().lambda();
            ArticleComment articleComment = baseMapper.selectOne(queryWrapper.select(ArticleComment::getArticleId).eq(ArticleComment::getCommentId, commentId));
            Long articleId = articleComment.getArticleId();
            // 创建消息通知
            messageNotificationProducer.likeCreate(uid, nickname, articleId, MessageNotificationMainTypeEnum.ARTICLE, commentId, MessageNotificationTargetTypeEnum.ARTICLE_COMMENT);
        }
        return positiveOperate;
    }

    @Override
    public boolean dislike(Long commentId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        boolean negativeOperate = articleCommentLikeStatistics.setNegativeOperate(commentId, uid);
        if (negativeOperate) {
            // 查询文章id
            LambdaQueryWrapper<ArticleComment> queryWrapper = new QueryWrapper<ArticleComment>().lambda();
            ArticleComment articleComment = baseMapper.selectOne(queryWrapper.select(ArticleComment::getArticleId).eq(ArticleComment::getCommentId, commentId));
            Long articleId = articleComment.getArticleId();
            // 创建消息通知
            messageNotificationProducer.dislikeCreate(uid, nickname, articleId, MessageNotificationMainTypeEnum.ARTICLE, commentId, MessageNotificationTargetTypeEnum.ARTICLE_COMMENT);
        }
        return negativeOperate;
    }

    @Override
    public void removeOne(Long commentId) {
        long uid = tokenFeignService.getUid();
        LambdaQueryWrapper<ArticleComment> articleCommentWrapper = new QueryWrapper<ArticleComment>().lambda();
        int delete = baseMapper.delete(articleCommentWrapper.eq(ArticleComment::getCommentId, commentId).eq(ArticleComment::getUid, uid));
        String message = "\n没有删除权限,\ncommentId:" + commentId + ",\nuid:" + uid;
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(message));
        // todo:异步删除，同时删除点赞和热度消息（热度可能会添加过期时间）
        LambdaQueryWrapper<ArticleCommentChild> articleCommentChildWrapper = new QueryWrapper<ArticleCommentChild>().lambda();
        articleCommentChildMapper.delete(articleCommentChildWrapper.eq(ArticleCommentChild::getParentId, commentId));
    }
}
