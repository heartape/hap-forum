package com.heartape.hap.business.controller;


import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.DiscussCommentBO;
import com.heartape.hap.business.entity.dto.DiscussCommentDTO;
import com.heartape.hap.business.entity.ro.DiscussCommentRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.IDiscussCommentService;
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
@RequestMapping("/business/topic/discuss/comment")
@Api(tags = "讨论评论")
public class DiscussCommentController {

    @Autowired
    private IDiscussCommentService discussCommentService;

    @PostMapping
    @ApiOperation("创建讨论评论")
    public Result create(@RequestBody DiscussCommentRO discussCommentRO) {
        DiscussCommentDTO discussCommentDTO = new DiscussCommentDTO();
        BeanUtils.copyProperties(discussCommentRO, discussCommentDTO);
        discussCommentService.create(discussCommentDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("讨论评论列表")
    public Result list(@RequestParam Long discussId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<DiscussCommentBO> discussComment = discussCommentService.list(discussId, pageNum, pageSize);
        return Result.success(discussComment);
    }

    @PutMapping("/like")
    @ApiOperation("点赞文章")
    public Result like(@RequestParam Long commentId) {
        discussCommentService.like(commentId);
        return Result.success();
    }

    @PutMapping("/dislike")
    @ApiOperation("点踩文章")
    public Result dislike(@RequestParam Long commentId) {
        discussCommentService.dislike(commentId);
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("删除讨论评论")
    public Result remove(@RequestParam Long commentId) {
        discussCommentService.remove(commentId);
        return Result.success();
    }
}
