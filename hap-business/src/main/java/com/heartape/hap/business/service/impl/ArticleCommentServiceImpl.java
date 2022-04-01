package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.Article;
import com.heartape.hap.business.entity.ArticleComment;
import com.heartape.hap.business.entity.ArticleCommentChild;
import com.heartape.hap.business.entity.bo.ArticleCommentBO;
import com.heartape.hap.business.entity.dto.ArticleCommentDTO;
import com.heartape.hap.business.exception.PermissionNoRemoveException;
import com.heartape.hap.business.exception.RelyDataNotExistedException;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import com.heartape.hap.business.mapper.ArticleCommentChildMapper;
import com.heartape.hap.business.mapper.ArticleCommentMapper;
import com.heartape.hap.business.mapper.ArticleMapper;
import com.heartape.hap.business.service.IArticleCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.business.utils.AssertUtils;
import com.heartape.hap.business.utils.SqlUtils;
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
    private SqlUtils sqlUtils;

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

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
            articleCommentBO.setLike(124);
            articleCommentBO.setDislike(13);
            return articleCommentBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(commentBOs);
        return boPageInfo;
    }

    @Override
    public void removeOne(Long commentId) {
        long uid = tokenFeignService.getUid();
        int delete = baseMapper.delete(new QueryWrapper<ArticleComment>().eq("comment_id", commentId).eq("uid", uid));
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(String.format("没有删除权限,commentId:%s,uid:%s", commentId, uid)));
        articleCommentChildMapper.delete(new QueryWrapper<ArticleCommentChild>().eq("parent_id", commentId));
    }
}
