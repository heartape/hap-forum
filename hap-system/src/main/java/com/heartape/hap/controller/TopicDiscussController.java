package com.heartape.hap.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.entity.bo.TopicDiscussBO;
import com.heartape.hap.entity.dto.TopicDiscussDTO;
import com.heartape.hap.entity.ro.TopicDiscussRO;
import com.heartape.hap.response.Result;
import com.heartape.hap.service.ITopicDiscussService;
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
@RequestMapping("/system/topic/discuss")
@Api(tags = "讨论")
public class TopicDiscussController {

    @Autowired
    private ITopicDiscussService topicDiscussService;

    @PostMapping
    @ApiOperation("创建讨论")
    public Result create(@RequestBody TopicDiscussRO topicDiscuss) {
        TopicDiscussDTO topicDiscussDTO = new TopicDiscussDTO();
        BeanUtils.copyProperties(topicDiscuss, topicDiscussDTO);
        topicDiscussService.create(topicDiscussDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("讨论列表")
    public Result list(@RequestParam Long topicId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<TopicDiscussBO> topic = topicDiscussService.list(topicId, pageNum, pageSize);
        return Result.success(topic);
    }

    @PutMapping("/like")
    @ApiOperation("点赞讨论")
    public Result like(@RequestParam Long discussId) {
        AbstractTypeOperateStatistics.TypeNumber like = topicDiscussService.like(discussId);
        return Result.success(like);
    }

    @PutMapping("/dislike")
    @ApiOperation("点赞讨论")
    public Result dislike(@RequestParam Long discussId) {
        AbstractTypeOperateStatistics.TypeNumber dislike = topicDiscussService.dislike(discussId);
        return Result.success(dislike);
    }

    @DeleteMapping
    @ApiOperation("删除讨论")
    public Result remove(@RequestParam Long discussId) {
        topicDiscussService.remove(discussId);
        return Result.success();
    }
}
