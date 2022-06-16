package com.heartape.hap.controller;

import com.heartape.hap.entity.Creator;
import com.heartape.hap.entity.HapUserDetails;
import com.heartape.hap.response.Result;
import com.heartape.hap.service.ITokenService;
import com.heartape.hap.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oauth")
@Api(tags = "认证内部接口")
public class TokenController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ITokenService tokenService;

    @GetMapping("/uid")
    @ApiOperation("获取uid")
    public Result uid() {
        long uid = tokenUtils.getUid();
        return Result.success(uid);
    }

    @GetMapping("/token")
    @ApiOperation("获取token信息")
    public Result token() {
        HapUserDetails info = tokenUtils.getUserDetails();
        return Result.success(info);
    }

    @PostMapping("/token")
    @ApiOperation("创建token")
    public Result createToken(@RequestBody HapUserDetails userDetails) {
        String token = tokenUtils.create(userDetails);
        return Result.success(token);
    }

    @GetMapping("/login/mail/password")
    @ApiOperation("gateway登陆时调用数据库查询用户信息")
    public Result mailPasswordLogin(String email) {
        Creator creator = tokenService.mailPasswordLogin(email);
        return Result.success(creator);
    }

}
