package com.heartape.hap.business.controller;

import com.heartape.hap.business.entity.ro.TopicRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
