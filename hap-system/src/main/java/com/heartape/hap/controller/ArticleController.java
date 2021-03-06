package com.heartape.hap.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.constant.HeatDeltaEnum;
import com.heartape.hap.entity.bo.ArticleSimpleBO;
import com.heartape.hap.entity.bo.ArticleBO;
import com.heartape.hap.entity.dto.ArticleDTO;
import com.heartape.hap.entity.ro.ArticleRO;
import com.heartape.hap.response.Result;
import com.heartape.hap.service.IArticleService;
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
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/system/article")
@Api(tags = "文章")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @PostMapping
    @ApiOperation("创建文章")
    public Result create(@RequestBody ArticleRO article) {
        ArticleDTO articleDTO = new ArticleDTO();
        BeanUtils.copyProperties(article, articleDTO);
        articleService.create(articleDTO);
        return Result.success();
    }

    @GetMapping("/list/hot")
    @ApiOperation("热点文章列表")
    public Result list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<ArticleSimpleBO> articles = articleService.list(pageNum, pageSize);
        return Result.success(articles);
    }

    @GetMapping
    @ApiOperation("文章详情")
    public Result detail(@RequestParam Long articleId) {
        ArticleBO article = articleService.detail(articleId);
        articleService.heatChange(articleId, HeatDeltaEnum.ARTICLE_SELECT.getDelta());
        return Result.success(article);
    }

    @PutMapping("/like")
    @ApiOperation("点赞文章")
    public Result like(@RequestParam Long articleId) {
        AbstractTypeOperateStatistics.TypeNumber like = articleService.like(articleId);
        return Result.success(like);
    }

    @PutMapping("/dislike")
    @ApiOperation("踩文章")
    public Result dislike(@RequestParam Long articleId) {
        AbstractTypeOperateStatistics.TypeNumber dislike = articleService.dislike(articleId);
        return Result.success(dislike);
    }

    @DeleteMapping
    @ApiOperation("删除文章")
    public Result remove(@RequestParam Long articleId) {
        articleService.remove(articleId);
        return Result.success();
    }
}
