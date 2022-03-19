package com.heartape.hap.business.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.ArticleBO;
import com.heartape.hap.business.entity.bo.ArticleDetailBO;
import com.heartape.hap.business.entity.dto.ArticleDTO;
import com.heartape.hap.business.entity.ro.ArticleRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.IArticleService;
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
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @PostMapping
    public Result publish(@RequestBody ArticleRO article) {
        ArticleDTO articleDTO = new ArticleDTO();
        BeanUtils.copyProperties(article, articleDTO);
        articleService.publish(articleDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(@RequestParam Integer page, @RequestParam Integer size) {
        PageInfo<ArticleBO> articles = articleService.list(page, size);
        return Result.success(articles);
    }

    @GetMapping("/list/creator")
    public Result creatorList(@RequestParam Integer page, @RequestParam Integer size) {
        PageInfo<ArticleBO> articles = articleService.creatorList(page, size);
        return Result.success(articles);
    }

    @GetMapping
    public Result detail(@RequestParam Long articleId) {
        ArticleDetailBO article = articleService.detail(articleId);
        return Result.success(article);
    }

    @DeleteMapping
    public Result remove(@RequestParam Long articleId) {
        articleService.remove(articleId);
        return Result.success();
    }
}
