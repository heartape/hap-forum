package com.heartape.hap.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.entity.bo.ArticleCommentChildBO;
import com.heartape.hap.entity.dto.ArticleCommentChildDTO;
import com.heartape.hap.entity.ro.ArticleCommentChildRO;
import com.heartape.hap.response.Result;
import com.heartape.hap.service.IArticleCommentChildService;
import com.heartape.hap.statistics.AbstractTypeOperateStatistics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/system/article/comment/child")
@Api(tags = "文章子评论")
public class ArticleCommentChildController {
    @Autowired
    private IArticleCommentChildService articleCommentChildrenService;

    @PostMapping
    @ApiOperation("创建文章子评论")
    public Result create(@RequestBody ArticleCommentChildRO articleCommentChildRO) {
        ArticleCommentChildDTO articleCommentChildDTO = new ArticleCommentChildDTO();
        BeanUtils.copyProperties(articleCommentChildRO, articleCommentChildDTO);
        articleCommentChildrenService.create(articleCommentChildDTO);
        return Result.success();
    }

    @PostMapping("/to-child")
    @ApiOperation("创建文章子评论间子评论")
    public Result createToChild(@RequestBody ArticleCommentChildRO articleCommentChildRO) {
        ArticleCommentChildDTO articleCommentChildDTO = new ArticleCommentChildDTO();
        BeanUtils.copyProperties(articleCommentChildRO, articleCommentChildDTO);
        articleCommentChildrenService.createToChild(articleCommentChildDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("文章子评论列表")
    public Result list(@RequestParam Long commentId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<ArticleCommentChildBO> articleCommentChildren = articleCommentChildrenService.list(commentId, pageNum, pageSize);
        return Result.success(articleCommentChildren);
    }

    @PutMapping("/like")
    @ApiOperation("点赞评论")
    public Result like(@RequestParam Long commentId) {
        AbstractTypeOperateStatistics.TypeNumber like = articleCommentChildrenService.like(commentId);
        return Result.success(like);
    }

    @PutMapping("/dislike")
    @ApiOperation("点赞评论")
    public Result dislike(@RequestParam Long commentId) {
        AbstractTypeOperateStatistics.TypeNumber dislike = articleCommentChildrenService.dislike(commentId);
        return Result.success(dislike);
    }

    @DeleteMapping
    @ApiOperation("删除文章子评论")
    public Result remove(@RequestParam Long commentId) {
        articleCommentChildrenService.remove(commentId);
        return Result.success();
    }
}
