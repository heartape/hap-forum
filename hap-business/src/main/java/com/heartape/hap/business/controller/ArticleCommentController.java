package com.heartape.hap.business.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.ArticleCommentBO;
import com.heartape.hap.business.entity.dto.ArticleCommentDTO;
import com.heartape.hap.business.entity.ro.ArticleCommentRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.IArticleCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

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
@Api(tags = "文章评论")
public class ArticleCommentController {

    @Autowired
    private IArticleCommentService articleCommentService;

    @PostMapping
    @ApiOperation("创建文章评论")
    public Result create(@RequestBody ArticleCommentRO articleCommentRO) {
        ArticleCommentDTO articleCommentDTO = new ArticleCommentDTO();
        BeanUtils.copyProperties(articleCommentRO, articleCommentDTO);
        articleCommentService.create(articleCommentDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("文章评论列表")
    public Result list(@RequestParam Long articleId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<ArticleCommentBO> articleComment = articleCommentService.list(articleId, pageNum, pageSize);
        return Result.success(articleComment);
    }

    @DeleteMapping
    @ApiOperation("删除文章评论")
    public Result remove(@RequestParam Long commentId) {
        articleCommentService.removeOne(commentId);
        return Result.success();
    }
}
