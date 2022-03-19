package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.Article;
import com.heartape.hap.business.entity.ArticleComment;
import com.heartape.hap.business.entity.ArticleCommentChild;
import com.heartape.hap.business.entity.bo.*;
import com.heartape.hap.business.entity.dto.ArticleDTO;
import com.heartape.hap.business.exception.PermissionNoRemoveException;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import com.heartape.hap.business.mapper.ArticleCommentChildMapper;
import com.heartape.hap.business.mapper.ArticleCommentMapper;
import com.heartape.hap.business.mapper.ArticleMapper;
import com.heartape.hap.business.service.IArticleCommentService;
import com.heartape.hap.business.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.business.utils.AssertUtils;
import com.heartape.hap.business.utils.StringTransformUtils;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    private IArticleCommentService articleCommentService;

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

    @Override
    public void publish(ArticleDTO articleDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleDTO, article);
        String content = article.getContent();
        String s = Jsoup.clean(content, Safelist.none());
        String ignoreBlank = stringTransformUtils.IgnoreBlank(s);
        String simpleContent = ignoreBlank.substring(0, 100);
        article.setSimpleContent(simpleContent);
        this.baseMapper.insert(article);
    }

    @Override
    public PageInfo<ArticleBO> list(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Article> list = query().list();
        PageInfo<Article> pageInfo = PageInfo.of(list);

        List<ArticleBO> collect = list.stream().map(article -> {
            ArticleBO articleBO = new ArticleBO();
            BeanUtils.copyProperties(article, articleBO);
            return articleBO;
        }).collect(Collectors.toList());

        PageInfo<ArticleBO> copyPageInfo = PageInfo.emptyPageInfo();
        BeanUtils.copyProperties(pageInfo, copyPageInfo);
        copyPageInfo.setList(collect);
        return copyPageInfo;
    }

    @Override
    public PageInfo<ArticleBO> creatorList(Integer page, Integer size) {
        long uid = tokenFeignService.getUid();
        PageHelper.startPage(page, size);
        List<Article> list = query().eq("uid",uid).orderByDesc("created_time").list();
        PageInfo<Article> pageInfo = PageInfo.of(list);

        List<ArticleBO> collect = list.stream().map(article -> {
            ArticleBO articleBO = new ArticleBO();
            BeanUtils.copyProperties(article, articleBO);
            return articleBO;
        }).collect(Collectors.toList());

        PageInfo<ArticleBO> copyPageInfo = PageInfo.emptyPageInfo();
        BeanUtils.copyProperties(pageInfo, copyPageInfo);
        copyPageInfo.setList(collect);
        return copyPageInfo;
    }

    @Override
    public ArticleDetailBO detail(Long articleId) {
        Article article = this.baseMapper.selectOneLabel(articleId);
        ArticleDetailBO articleDetailBO = new ArticleDetailBO();
        BeanUtils.copyProperties(article, articleDetailBO);
        // 转换label
        List<LabelBO> label = article.getLabel().stream().map(item -> {
            LabelBO labelBO = new LabelBO();
            BeanUtils.copyProperties(item, labelBO);
            return labelBO;
        }).collect(Collectors.toList());
        articleDetailBO.setLabel(label);

        PageInfo<ArticleCommentBO> commentBO = articleCommentService.list(articleId, 1, 5);
        articleDetailBO.setComment(commentBO);
        return articleDetailBO;
    }

    @Override
    public void remove(Long articleId) {
        long uid = tokenFeignService.getUid();
        int delete = baseMapper.delete(new QueryWrapper<Article>().eq("article_id", articleId).eq("uid", uid));
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(String.format("没有删除权限,articleId:%s,uid:%s", articleId, uid)));
        // todo:考虑是否需要rabbitmq异步删除评论
        articleCommentMapper.delete(new QueryWrapper<ArticleComment>().eq("article_id", articleId));
        articleCommentChildMapper.delete(new QueryWrapper<ArticleCommentChild>().eq("article_id", articleId));
    }
}
