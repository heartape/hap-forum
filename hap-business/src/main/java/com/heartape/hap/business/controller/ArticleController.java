package com.heartape.hap.business.controller;

import com.heartape.hap.business.entity.Article;
import com.heartape.hap.business.mapper.ArticleMapper;
import com.heartape.hap.business.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private ArticleMapper articleMapper;

    @PostMapping
    public Result publish() {
        Article article = new Article();
        article.setContent("qrqweqw");
        article.setTitle("qwrqw");
        article.setPublishTime(LocalDateTime.now());
        article.setUid(1L);
        List<Long> labels = new ArrayList<>();
        labels.add(1L);
        article.setLabelId(labels);
        articleMapper.insert(article);
        return Result.success();
    }
}
