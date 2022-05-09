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
import com.heartape.hap.business.entity.dto.ArticleCommentDTO;
import com.heartape.hap.business.exception.PermissionNoRemoveException;
import com.heartape.hap.business.exception.RelyDataNotExistedException;
import com.heartape.hap.business.exception.ResourceOperateRepeatException;
import com.heartape.hap.business.feign.HapUserDetails;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import com.heartape.hap.business.mapper.ArticleCommentChildMapper;
import com.heartape.hap.business.mapper.ArticleCommentMapper;
import com.heartape.hap.business.mapper.ArticleMapper;
import com.heartape.hap.business.mq.producer.IMessageNotificationProducer;
import com.heartape.hap.business.service.IArticleCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.business.statistics.ArticleCommentLikeStatistics;
import com.heartape.hap.business.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public void create(ArticleCommentDTO articleCommentDTO) {
        Long articleId = articleCommentDTO.getArticleId();
        Long count = articleMapper.selectCount(new QueryWrapper<Article>().eq("article_id", articleId));
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(String.format("ArticleComment所依赖的Article:id=%s不存在", articleId)));

        ArticleComment articleComment = new ArticleComment();
        BeanUtils.copyProperties(articleCommentDTO, articleComment);
        this.baseMapper.insert(articleComment);
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
            // todo:从热度统计中获取较高的子评论
            articleCommentBO.setSimpleChildren(new ArrayList<>());
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
    public void like(Long commentId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        boolean b = articleCommentLikeStatistics.setPositiveOperate(commentId, uid);
        assertUtils.businessState(b, new ResourceOperateRepeatException("文章评论:" + commentId + ",用户:" + uid + "已经进行过点赞"));
        // 查询文章id
        LambdaQueryWrapper<ArticleComment> queryWrapper = new QueryWrapper<ArticleComment>().lambda();
        ArticleComment articleComment = baseMapper.selectOne(queryWrapper.select(ArticleComment::getArticleId).eq(ArticleComment::getCommentId, commentId));
        Long articleId = articleComment.getArticleId();
        messageNotificationProducer.likeCreate(uid, nickname, articleId, MessageNotificationMainTypeEnum.ARTICLE, commentId, MessageNotificationTargetTypeEnum.ARTICLE_COMMENT);
    }

    @Override
    public void removeOne(Long commentId) {
        long uid = tokenFeignService.getUid();
        int delete = baseMapper.delete(new QueryWrapper<ArticleComment>().eq("comment_id", commentId).eq("uid", uid));
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(String.format("没有删除权限,commentId:%s,uid:%s", commentId, uid)));
        articleCommentChildMapper.delete(new QueryWrapper<ArticleCommentChild>().eq("parent_id", commentId));
    }
}
