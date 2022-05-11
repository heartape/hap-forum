package com.heartape.hap.api.controller;

import com.heartape.hap.api.response.Result;
import com.heartape.hap.api.service.ICreatorService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/creator/info")
public class CreatorController {

    @Autowired
    private ICreatorService creatorService;

    @GetMapping("/nickname")
    @ApiOperation("获取nickname")
    public Result nickname(@RequestParam Long uid) {
        String nickname = creatorService.nickname(uid);
        return Result.success(nickname);
    }
}
