package com.heartape.hap.controller;

import com.heartape.hap.entity.bo.CreatorDetailBO;
import com.heartape.hap.entity.bo.CreatorProduceDataBO;
import com.heartape.hap.response.Result;
import com.heartape.hap.service.ICreatorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system/creator")
public class CreatorController {

    @Autowired
    private ICreatorService creatorService;

    @GetMapping("/detail")
    @ApiOperation("用户主页信息")
    public Result detail(@RequestParam Long uid) {
        CreatorDetailBO creatorDetailBO = creatorService.detail(uid);
        return Result.success(creatorDetailBO);
    }

    @GetMapping("/data")
    @ApiOperation("用户创作信息")
    public Result data(@RequestParam Long uid) {
        CreatorProduceDataBO creatorProduceDataBO = creatorService.data(uid);
        return Result.success(creatorProduceDataBO);
    }
}
