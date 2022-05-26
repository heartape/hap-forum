package com.heartape.hap.controller;

import com.heartape.hap.entity.Creator;
import com.heartape.hap.entity.HapUserDetails;
import com.heartape.hap.entity.LoginCode;
import com.heartape.hap.entity.LoginForm;
import com.heartape.hap.entity.bo.CreatorBO;
import com.heartape.hap.entity.ro.LoginCodeRO;
import com.heartape.hap.response.Result;
import com.heartape.hap.service.ITokenService;
import com.heartape.hap.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oauth")
@Api(tags = "认证相关接口")
public class TokenController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ITokenService tokenService;

    @ApiOperation("获取验证码")
    @GetMapping("/code")
    public Result getCode() {
        LoginCode loginCode = tokenUtils.newCode();
        return Result.success(loginCode);
    }

    @ApiOperation("校验验证码")
    @PostMapping("/code")
    public Result checkCode(@RequestBody LoginCodeRO loginCode) {
        boolean check = tokenUtils.checkCode(loginCode);
        return Result.success(check);
    }

    @PostMapping("/token")
    @ApiOperation("创建token")
    public Result createToken(@RequestBody HapUserDetails userDetails) {
        String token = tokenUtils.create(userDetails);
        return Result.success(token);
    }

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

    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public Result getInfo() {
        HapUserDetails userDetails = tokenUtils.getUserDetails();
        CreatorBO creatorBO = new CreatorBO();
        BeanUtils.copyProperties(userDetails, creatorBO);
        return Result.success(creatorBO);
    }

    @GetMapping("/login/mail/password")
    @ApiOperation("gateway登陆时调用数据库查询用户信息")
    public Result mailPasswordLogin(String email) {
        Creator creator = tokenService.mailPasswordLogin(email);
        return Result.success(creator);
    }

    @PostMapping("/login/mail/code")
    @ApiOperation("邮箱验证码登录")
    public Result mailCodeLogin(@RequestBody LoginForm loginForm) {
        return Result.success();
    }

    @PostMapping("/login/phone/code")
    @ApiOperation("短信验证码登录")
    public Result phoneCodeLogin(@RequestBody LoginForm loginForm) {
        return Result.success();
    }
}
