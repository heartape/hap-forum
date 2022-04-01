package com.heartape.hap.business.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.ArticleSimpleBO;
import com.heartape.hap.business.entity.bo.ArticleBO;
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

    @GetMapping("/list/hot")
    public Result list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<ArticleSimpleBO> articles = articleService.list(pageNum, pageSize);
        return Result.success(articles);
    }

    @GetMapping
    public Result detail(@RequestParam Long articleId) {
        ArticleBO article = articleService.detail(articleId);
        return Result.success(article);
    }

    @DeleteMapping
    public Result remove(@RequestParam Long articleId) {
        articleService.remove(articleId);
        return Result.success();
    }
}
