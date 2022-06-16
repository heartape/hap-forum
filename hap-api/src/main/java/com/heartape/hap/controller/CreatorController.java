package com.heartape.hap.controller;

import com.heartape.hap.entity.HapUserDetails;
import com.heartape.hap.entity.bo.CreatorBO;
import com.heartape.hap.response.Result;
import com.heartape.hap.service.ICreatorService;
import com.heartape.hap.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/creator")
@Api(tags = "用户信息")
public class CreatorController {

    @Autowired
    private ICreatorService creatorService;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * todo:如果没有异步加载昵称的需求则将该接口删除
     */
    @GetMapping("/nickname")
    @ApiOperation("获取nickname")
    public Result nickname(@RequestParam Long uid) {
        String nickname = creatorService.nickname(uid);
        return Result.success(nickname);
    }

    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public Result getInfo() {
        HapUserDetails userDetails = tokenUtils.getUserDetails();
        CreatorBO creatorBO = new CreatorBO();
        BeanUtils.copyProperties(userDetails, creatorBO);
        return Result.success(creatorBO);
    }
}
