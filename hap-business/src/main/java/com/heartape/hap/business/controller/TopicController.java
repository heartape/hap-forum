package com.heartape.hap.business.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.TopicBO;
import com.heartape.hap.business.entity.bo.TopicSimpleBO;
import com.heartape.hap.business.entity.ro.TopicRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.ITopicService;
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
@RequestMapping("/business/topic")
public class TopicController {

    @Autowired
    private ITopicService topicService;

    @PostMapping
    public Result create(@RequestBody TopicRO topic) {
        topicService.create(topic);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(@RequestParam Integer page, @RequestParam Integer size) {
        PageInfo<TopicSimpleBO> topic = topicService.list(page, size);
        return Result.success(topic);
    }

    @GetMapping("/list/creator")
    public Result creatorList(@RequestParam Integer page, @RequestParam Integer size) {
        PageInfo<TopicSimpleBO> topic = topicService.creatorList(page, size);
        return Result.success(topic);
    }

    @GetMapping
    public Result detail(@RequestParam Long topicId) {
        TopicBO topic = topicService.detail(topicId);
        return Result.success(topic);
    }

    @DeleteMapping
    public Result remove(@RequestParam Long topicId) {
        topicService.remove(topicId);
        return Result.success();
    }
}
