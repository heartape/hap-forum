package com.heartape.hap.business.controller;


import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.DiscussCommentBO;
import com.heartape.hap.business.entity.dto.DiscussCommentDTO;
import com.heartape.hap.business.entity.ro.DiscussCommentRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.IDiscussCommentService;
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
public class DiscussCommentController {

    @Autowired
    private IDiscussCommentService discussCommentService;

    @PostMapping
    public Result create(@RequestBody DiscussCommentRO discussCommentRO) {
        DiscussCommentDTO discussCommentDTO = new DiscussCommentDTO();
        BeanUtils.copyProperties(discussCommentRO, discussCommentDTO);
        discussCommentService.create(discussCommentDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(@RequestParam Long discussId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<DiscussCommentBO> discussComment = discussCommentService.list(discussId, pageNum, pageSize);
        return Result.success(discussComment);
    }

    @DeleteMapping
    public Result remove(@RequestParam Long commentId) {
        discussCommentService.remove(commentId);
        return Result.success();
    }
}
