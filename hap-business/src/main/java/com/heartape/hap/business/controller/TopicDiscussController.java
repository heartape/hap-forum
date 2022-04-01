package com.heartape.hap.business.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.TopicDiscussBO;
import com.heartape.hap.business.entity.dto.TopicDiscussDTO;
import com.heartape.hap.business.entity.ro.TopicDiscussRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.ITopicDiscussService;
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
@RequestMapping("/business/topic/discuss")
public class TopicDiscussController {

    @Autowired
    private ITopicDiscussService topicDiscussService;

    @PostMapping
    public Result create(@RequestBody TopicDiscussRO topicDiscuss) {
        TopicDiscussDTO topicDiscussDTO = new TopicDiscussDTO();
        BeanUtils.copyProperties(topicDiscuss, topicDiscussDTO);
        topicDiscussService.create(topicDiscussDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list(@RequestParam Long topicId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<TopicDiscussBO> topic = topicDiscussService.list(topicId, pageNum, pageSize);
        return Result.success(topic);
    }

    @DeleteMapping
    public Result remove(@RequestParam Long topicId) {
        topicDiscussService.remove(topicId);
        return Result.success();
    }
}
