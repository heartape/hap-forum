package com.heartape.hap.business.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.ArticleCommentChildBO;
import com.heartape.hap.business.entity.bo.DiscussCommentChildBO;
import com.heartape.hap.business.entity.dto.ArticleCommentChildDTO;
import com.heartape.hap.business.entity.dto.DiscussCommentChildDTO;
import com.heartape.hap.business.entity.ro.ArticleCommentChildRO;
import com.heartape.hap.business.entity.ro.DiscussCommentChildRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.IArticleCommentChildService;
import com.heartape.hap.business.service.IDiscussCommentChildService;
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
@RequestMapping("/business/topic/discuss/comment/child")
@Api(tags = "讨论子评论")
public class DiscussCommentChildController {

    @Autowired
    private IDiscussCommentChildService discussCommentChildService;

    @PostMapping
    @ApiOperation("创建讨论子评论")
    public Result create(@RequestBody DiscussCommentChildRO discussCommentChildRO) {
        DiscussCommentChildDTO discussCommentChildDTO = new DiscussCommentChildDTO();
        BeanUtils.copyProperties(discussCommentChildRO, discussCommentChildDTO);
        discussCommentChildService.create(discussCommentChildDTO);
        return Result.success();
    }

    @PostMapping("/to-child")
    @ApiOperation("创建讨论子评论间子评论")
    public Result createToChild(@RequestBody DiscussCommentChildRO discussCommentChildRO) {
        DiscussCommentChildDTO discussCommentChildDTO = new DiscussCommentChildDTO();
        BeanUtils.copyProperties(discussCommentChildRO, discussCommentChildDTO);
        discussCommentChildService.createToChild(discussCommentChildDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("讨论子评论列表")
    public Result list(@RequestParam Long commentId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<DiscussCommentChildBO> discussCommentChild = discussCommentChildService.list(commentId, pageNum, pageSize);
        return Result.success(discussCommentChild);
    }

    @PutMapping("/like")
    @ApiOperation("点赞评论")
    public Result like(@RequestParam Long commentId) {
        discussCommentChildService.like(commentId);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("删除讨论子评论")
    public Result remove(@RequestParam Long commentId) {
        discussCommentChildService.remove(commentId);
        return Result.success();
    }
}
