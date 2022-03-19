package com.heartape.hap.business.controller;

import com.heartape.hap.business.response.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个人中心管理
 */
@RestController
@RequestMapping("/business/personal/manage")
public class PersonalCenterManageController {

    @GetMapping("/content/all")
    public Result contentAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Integer startTime, @RequestParam Integer endTime) {
        return Result.success();
    }

    @GetMapping("/content/article")
    public Result contentArticle(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Integer startTime, @RequestParam Integer endTime) {
        return Result.success();
    }

    @GetMapping("/content/topic")
    public Result contentTopic(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Integer startTime, @RequestParam Integer endTime) {
        return Result.success();
    }

    @GetMapping("/content/discuss")
    public Result contentDiscuss(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Integer startTime, @RequestParam Integer endTime) {
        return Result.success();
    }

    @GetMapping("/content/comment")
    public Result contentComment(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Integer startTime, @RequestParam Integer endTime) {
        return Result.success();
    }

    @GetMapping("/comment/all")
    public Result commentAll(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Integer startTime, @RequestParam Integer endTime) {
        return Result.success();
    }

    @GetMapping("/comment/article")
    public Result commentArticle(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Integer startTime, @RequestParam Integer endTime) {
        return Result.success();
    }

    @GetMapping("/comment/discuss")
    public Result commentDiscuss(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Integer startTime, @RequestParam Integer endTime) {
        return Result.success();
    }

    @GetMapping("/comment/comment")
    public Result commentComment(@RequestParam Integer pageNum, @RequestParam Integer pageSize, @RequestParam Integer startTime, @RequestParam Integer endTime) {
        return Result.success();
    }
}
