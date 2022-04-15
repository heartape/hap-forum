package com.heartape.hap.business.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.ArticleSimpleBO;
import com.heartape.hap.business.entity.bo.ArticleBO;
import com.heartape.hap.business.entity.dto.ArticleDTO;
import com.heartape.hap.business.entity.ro.ArticleRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.IArticleService;
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
@RequestMapping("/business/article")
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
        return Result.success(article);
    }

    @DeleteMapping
    @ApiOperation("删除文章")
    public Result remove(@RequestParam Long articleId) {
        articleService.remove(articleId);
        return Result.success();
    }
}
