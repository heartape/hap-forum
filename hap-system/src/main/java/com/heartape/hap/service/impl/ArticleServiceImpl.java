package com.heartape.hap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.constant.HeatDeltaEnum;
import com.heartape.hap.constant.MessageNotificationActionEnum;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
        // 创建文章
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
        log.info("articleId:{},设置初始热度为:{}", articleId, hot);
    }

    @Cacheable(value = "ar-hot", cacheManager = "caffeineCacheManager", key = "#page + ':' + #size")
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

    @Cacheable(value = "ar-detail", cacheManager = "caffeineCacheManager", key = "#articleId")
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
        return articleBO;
    }

    @Async
    @Override
    public void heatChange(Long articleId, Integer delta) {
        int i = articleHotStatistics.updateIncrement(articleId, delta);
        // todo:测试时使用
        log.info("文章查询热度增加，articleId：" + articleId + ",增加值：" + delta + ",当前热度：" + i);
    }

    @CacheEvict(value = "ar-detail", cacheManager = "caffeineCacheManager", key = "#articleId")
    @Override
    public  AbstractTypeOperateStatistics.TypeNumber like(Long articleId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        AbstractTypeOperateStatistics.TypeNumber insert = articleLikeStatistics.insert(uid, articleId, AbstractTypeOperateStatistics.TypeEnum.POSITIVE);
        createMessageNotification(uid, nickname, articleId, MessageNotificationActionEnum.LIKE);
        return insert;
    }

    @CacheEvict(value = "ar-detail", cacheManager = "caffeineCacheManager", key = "#articleId")
    @Override
    public  AbstractTypeOperateStatistics.TypeNumber dislike(Long articleId) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String nickname = tokenInfo.getNickname();
        AbstractTypeOperateStatistics.TypeNumber typeNumber = articleLikeStatistics.insert(uid, articleId, AbstractTypeOperateStatistics.TypeEnum.NEGATIVE);
        createMessageNotification(uid, nickname, articleId, MessageNotificationActionEnum.DISLIKE);
        return typeNumber;
    }

    @Async
    public void createMessageNotification(Long uid, String nickname, Long articleId, MessageNotificationActionEnum action) {
        if (!messageNotificationProducer.exists(uid, articleId, MessageNotificationMainTypeEnum.ARTICLE, articleId, MessageNotificationTargetTypeEnum.ARTICLE, action)) {
            messageNotificationProducer.create(uid, nickname, articleId, MessageNotificationMainTypeEnum.ARTICLE, articleId, MessageNotificationTargetTypeEnum.ARTICLE, action);
            // 增加热度
            if (action == MessageNotificationActionEnum.LIKE) {
                int i = articleHotStatistics.updateIncrement(articleId, HeatDeltaEnum.ARTICLE_LIKE.getDelta());
                // todo:测试完删除
                log.info("文章点赞热度增加，articleId：{},当前热度：{}", articleId, i);
            } else if (action == MessageNotificationActionEnum.DISLIKE) {
                int i = articleHotStatistics.updateIncrement(articleId, HeatDeltaEnum.ARTICLE_DISLIKE.getDelta());
                // todo:测试完删除
                log.info("文章点踩热度增加，articleId：{},当前热度：{}", articleId, i);
            }
        }
    }

    @CacheEvict(value = "ar-detail", cacheManager = "caffeineCacheManager", key = "#articleId")
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
