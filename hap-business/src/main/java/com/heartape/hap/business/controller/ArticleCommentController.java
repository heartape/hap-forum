package com.heartape.hap.business.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.ArticleCommentBO;
import com.heartape.hap.business.entity.dto.ArticleCommentDTO;
import com.heartape.hap.business.entity.ro.ArticleCommentRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.IArticleCommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@RestController
@RequestMapping("/business/article/comment")
public class ArticleCommentController {

    @Autowired
    private IArticleCommentService articleCommentService;

    @PostMapping
    public Result create(@RequestBody ArticleCommentRO articleCommentRO) {
        ArticleCommentDTO articleCommentDTO = new ArticleCommentDTO();
        BeanUtils.copyProperties(articleCommentRO, articleCommentDTO);
        articleCommentService.create(articleCommentDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(@RequestParam Long articleId, @RequestParam Integer page, @RequestParam Integer size) {
        PageInfo<ArticleCommentBO> articleComment = articleCommentService.list(articleId, page, size);
        return Result.success(articleComment);
    }

    @DeleteMapping
    public Result remove(@RequestParam Long commentId) {
        articleCommentService.removeOne(commentId);
        return Result.success();
    }
}
