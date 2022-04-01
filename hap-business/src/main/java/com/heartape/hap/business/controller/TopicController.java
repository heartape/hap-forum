package com.heartape.hap.business.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.TopicBO;
import com.heartape.hap.business.entity.bo.TopicSimpleBO;
import com.heartape.hap.business.entity.dto.TopicDTO;
import com.heartape.hap.business.entity.ro.TopicRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.ITopicService;
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
@RequestMapping("/business/topic")
public class TopicController {

    @Autowired
    private ITopicService topicService;

    @PostMapping
    public Result create(@RequestBody TopicRO topic) {
        TopicDTO topicDTO = new TopicDTO();
        BeanUtils.copyProperties(topic, topicDTO);
        topicService.create(topicDTO);
        return Result.success();
    }

    @GetMapping("/list/hot")
    public Result list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<TopicSimpleBO> topic = topicService.list(pageNum, pageSize);
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
