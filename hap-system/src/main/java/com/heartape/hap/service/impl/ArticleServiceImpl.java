package com.heartape.hap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.constant.HeatDeltaEnum;
import com.heartape.hap.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.entity.Article;
import com.heartape.hap.entity.ArticleComment;
import com.heartape.hap.entity.ArticleCommentChild;
import com.heartape.hap.entity.dto.ArticleDTO;
import com.heartape.hap.exception.ParamIsInvalidException;
import com.heartape.hap.exception.PermissionNoRemoveException;
import com.heartape.hap.feign.HapUserDetails;
import com.heartape.hap.feign.TokenFeignServiceImpl;
import com.heartape.hap.mapper.ArticleCommentChildMapper;
import com.heartape.hap.mapper.ArticleCommentMapper;
import com.heartape.hap.mapper.ArticleMapper;
import com.heartape.hap.mq.producer.IMessageNotificationProducer;
import com.heartape.hap.statistics.AbstractTypeOperateStatistics;
import com.heartape.hap.statistics.ArticleHotStatistics;
import com.heartape.hap.statistics.ArticleLikeStatistics;
import com.heartape.hap.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.utils.AssertUtils;
import com.heartape.hap.utils.StringTransformUtils;
import com.heartape.hap.entity.bo.ArticleBO;
import com.heartape.hap.entity.bo.ArticleSimpleBO;
import com.heartape.hap.entity.bo.LabelBO;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    @Autowired
    private ArticleCommentChildMapper articleCommentChildMapper;

    @Autowired
    private StringTransformUtils stringTransformUtils;

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Autowired
    private AssertUtils assertUtils;

    @Autowired
    private IMessageNotificationProducer messageNotificationProducer;

    @Autowired
    private ArticleLikeStatistics articleLikeStatistics;

    @Autowired
    private ArticleHotStatistics articleHotStatistics;

    @Override
    public void create(ArticleDTO articleDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        String content = article.getContent();
        String s = Jsoup.clean(content, Safelist.none());
        String ignoreBlank = stringTransformUtils.IgnoreBlank(s);
        int length = ignoreBlank.length();
        String message = "\n文章长度低于指定范围:\n长度=" + length + "\n内容=" + ignoreBlank;
        assertUtils.businessState(length > 100, new ParamIsInvalidException(message));
        String simpleContent = ignoreBlank.substring(0, 100);
        article.setSimpleContent(simpleContent);

        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String avatar = tokenInfo.getAvatar();
        String nickname = tokenInfo.getNickname();
        String profile = tokenInfo.getProfile();
        article.setUid(uid);
        article.setAvatar(avatar);
        article.setNickname(nickname);
        article.setProfile(profile);
        this.baseMapper.insert(article);
        // 初始化热度
        Long articleId = article.getArticleId();
        int hot = articleHotStatistics.updateIncrement(articleId, HeatDeltaEnum.ARTICLE_INIT.getDelta());
        log.info("articleId:" + articleId + "设置初始热度为" + hot);
    }

    @Override
    public PageInfo<ArticleSimpleBO> list(Integer page, Integer size) {
        LambdaQueryWrapper<Article> queryWrapper = new QueryWrapper<Article>().lambda();
        PageHelper.startPage(page, size);
        List<Article> list = baseMapper.selectList(queryWrapper.select(Article::getArticleId,Article::getIsPicture,Article::getMainPicture,
                Article::getTitle,Article::getSimpleContent,Article::getCreatedTime).orderByDesc(Article::getCreatedTime));
        PageInfo<Article> pageInfo = PageInfo.of(list);

        List<ArticleSimpleBO> collect = list.stream().map(article -> {
            ArticleSimpleBO articleSimpleBO = new ArticleSimpleBO();
            BeanUtils.copyProperties(article, articleSimpleBO);
            // 点赞
            int likeNumber = articleLikeStatistics.selectPositiveNumber(article.getArticleId());
            int dislikeNumber = articleLikeStatistics.selectNegativeNumber(article.getArticleId());
            articleSimpleBO.setLike(likeNumber);
            articleSimpleBO.setDislike(dislikeNumber);
            return articleSimpleBO;
        }).collect(Collectors.toList());

        PageInfo<ArticleSimpleBO> copyPageInfo = PageInfo.emptyPageInfo();
        BeanUtils.copyProperties(pageInfo, copyPageInfo);
        copyPageInfo.setList(collect);
        return copyPageInfo;
    }

    @Override
    public ArticleBO detail(Long articleId) {
        Article article = this.baseMapper.selectOneLabel(articleId);
        ArticleBO articleBO = new ArticleBO();
        BeanUtils.copyProperties(article, articleBO);
        // 转换label
        List<LabelBO> label = article.getLabel().stream().map(item -> {
            LabelBO labelBO = new LabelBO();
            BeanUtils.copyProperties(item, labelBO);
            return labelBO;
        }).collect(Collectors.toList());
        articleBO.setLabel(label);
        int likeNumber = articleLikeStatistics.selectPositiveNumber(articleId);
        int dislikeNumber = articleLikeStatistics.selectNegativeNumber(articleId);
        articleBO.setLike(likeNumber);
        articleBO.setDislike(dislikeNumber);
        int delta = HeatDeltaEnum.ARTICLE_SELECT.getDelta();
        int i = articleHotStatistics.updateIncrement(articleId, delta);
        log.info("文章查询热度增加，articleId：" + articleId + ",增加值：" + delta + ",当前热度：" + i);
        return articleBO;
    }

    @Override
    public boolean like(Long articleId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        articleLikeStatistics.insert(articleId, uid, AbstractTypeOperateStatistics.TypeEnum.POSITIVE);
        if (true) {
            messageNotificationProducer.likeCreate(uid, nickname, articleId, MessageNotificationMainTypeEnum.ARTICLE, articleId, MessageNotificationTargetTypeEnum.ARTICLE);
            int delta = HeatDeltaEnum.ARTICLE_LIKE.getDelta();
            int i = articleHotStatistics.updateIncrement(articleId, delta);
            log.info("文章点赞热度增加，articleId：" + articleId + ",增加值：" + delta + ",当前热度：" + i);
        }
        return true;
    }

    @Override
    public boolean dislike(Long articleId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        articleLikeStatistics.insert(articleId, uid, AbstractTypeOperateStatistics.TypeEnum.NEGATIVE);
        if (true) {
            messageNotificationProducer.dislikeCreate(uid, nickname, articleId, MessageNotificationMainTypeEnum.ARTICLE, articleId, MessageNotificationTargetTypeEnum.ARTICLE);
            int delta = HeatDeltaEnum.ARTICLE_DISLIKE.getDelta();
            int i = articleHotStatistics.updateIncrement(articleId, delta);
            log.info("文章点赞热度增加，articleId：" + articleId + ",增加值：" + delta + ",当前热度：" + i);
        }
        return true;
    }

    @Override
    public void remove(Long articleId) {
        long uid = tokenFeignService.getUid();
        LambdaQueryWrapper<Article> queryWrapper = new QueryWrapper<Article>().lambda();
        int delete = baseMapper.delete(queryWrapper.eq(Article::getArticleId, articleId).eq(Article::getUid, uid));
        String message = "\n没有删除权限,\narticleId=" + articleId +",\nuid=" + uid;
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(message));
        // todo:删除点赞，热度等数据
        // todo:异步删除评论
        LambdaQueryWrapper<ArticleComment> articleCommentWrapper = new QueryWrapper<ArticleComment>().lambda();
        articleCommentMapper.delete(articleCommentWrapper.eq(ArticleComment::getArticleId, articleId));

        LambdaQueryWrapper<ArticleCommentChild> articleCommentChildWrapper = new QueryWrapper<ArticleCommentChild>().lambda();
        articleCommentChildMapper.delete(articleCommentChildWrapper.eq(ArticleCommentChild::getArticleId, articleId));
    }
}
