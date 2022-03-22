package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.ArticleComment;
import com.heartape.hap.business.entity.ArticleCommentChild;
import com.heartape.hap.business.entity.bo.ArticleCommentChildBO;
import com.heartape.hap.business.entity.dto.ArticleCommentChildDTO;
import com.heartape.hap.business.exception.PermissionNoRemoveException;
import com.heartape.hap.business.exception.RelyDataNotExistedException;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import com.heartape.hap.business.mapper.ArticleCommentChildMapper;
import com.heartape.hap.business.mapper.ArticleCommentMapper;
import com.heartape.hap.business.mapper.ArticleMapper;
import com.heartape.hap.business.service.IArticleCommentChildService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class ArticleCommentChildServiceImpl extends ServiceImpl<ArticleCommentChildMapper, ArticleCommentChild> implements IArticleCommentChildService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    @Autowired
    private AssertUtils assertUtils;

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Override
    public void create(ArticleCommentChildDTO articleCommentChildDTO) {

        Long articleId = articleCommentChildDTO.getArticleId();
        Long parentId = articleCommentChildDTO.getParentId();
        Long count = articleCommentMapper.selectCount(new QueryWrapper<ArticleComment>().eq("article_id", articleId).eq("comment_id", parentId));
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(String.format("ArticleCommentChildren所依赖的Article:id=%s或ArticleComment:id=%s不存在", articleId, parentId)));

        ArticleCommentChild articleCommentChild = new ArticleCommentChild();
        BeanUtils.copyProperties(articleCommentChildDTO, articleCommentChild);
        baseMapper.insert(articleCommentChild);
    }

    @Override
    public PageInfo<ArticleCommentChildBO> list(Long commentId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<ArticleCommentChild> commentChildren = query().select("comment_id","uid","avatar","nickname",
                "child_to_child","child_target","child_target_name","content","created_time")
                .eq("parent_id", commentId).list();
        PageInfo<ArticleCommentChild> childPageInfo = PageInfo.of(commentChildren);
        List<ArticleCommentChildBO> childBOList = commentChildren.stream().map(child -> {
            ArticleCommentChildBO childBO = new ArticleCommentChildBO();
            BeanUtils.copyProperties(child, childBO);
            return childBO;
        }).collect(Collectors.toList());
        PageInfo<ArticleCommentChildBO> childBOPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(childPageInfo, childBOPageInfo);
        childBOPageInfo.setList(childBOList);
        return childBOPageInfo;
    }

    @Override
    public void remove(Long commentId) {
        long uid = tokenFeignService.getUid();
        int delete = baseMapper.delete(new QueryWrapper<ArticleCommentChild>().eq("comment_id", commentId).eq("uid", uid));
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(String.format("没有ArticleCommentChild删除权限,commentId:%s,uid:%s", commentId, uid)));
    }
}