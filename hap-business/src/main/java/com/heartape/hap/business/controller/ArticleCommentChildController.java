package com.heartape.hap.business.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.ArticleCommentChildBO;
import com.heartape.hap.business.entity.dto.ArticleCommentChildDTO;
import com.heartape.hap.business.entity.ro.ArticleCommentChildRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.IArticleCommentChildService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@RestController
@RequestMapping("/business/article/comment/child")
public class ArticleCommentChildController {
    @Autowired
    private IArticleCommentChildService articleCommentChildrenService;

    @PostMapping
    public Result create(@RequestBody ArticleCommentChildRO articleCommentChildRO) {
        ArticleCommentChildDTO articleCommentChildDTO = new ArticleCommentChildDTO();
        BeanUtils.copyProperties(articleCommentChildRO, articleCommentChildDTO);
        articleCommentChildrenService.create(articleCommentChildDTO);
        return Result.success();
    }

    @PostMapping("/to-child")
    public Result createToChild(@RequestBody ArticleCommentChildRO articleCommentChildRO) {
        ArticleCommentChildDTO articleCommentChildDTO = new ArticleCommentChildDTO();
        BeanUtils.copyProperties(articleCommentChildRO, articleCommentChildDTO);
        articleCommentChildrenService.createToChild(articleCommentChildDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(@RequestParam Long commentId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<ArticleCommentChildBO> articleCommentChildren = articleCommentChildrenService.list(commentId, pageNum, pageSize);
        return Result.success(articleCommentChildren);
    }

    @DeleteMapping
    public Result remove(@RequestParam Long commentId) {
        articleCommentChildrenService.remove(commentId);
        return Result.success();
    }
}
