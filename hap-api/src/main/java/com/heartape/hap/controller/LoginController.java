package com.heartape.hap.controller;

import com.heartape.hap.entity.LoginCode;
import com.heartape.hap.entity.LoginForm;
import com.heartape.hap.entity.ro.LoginCodeRO;
import com.heartape.hap.response.Result;
import com.heartape.hap.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@Api(tags = "认证外部接口")
public class LoginController {

    @Autowired
    private TokenUtils tokenUtils;

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

    @PostMapping("/mail/code")
    @ApiOperation("邮箱验证码登录")
    public Result mailCodeLogin(@RequestBody LoginForm loginForm) {
        return Result.success();
    }

    @PostMapping("/phone/code")
    @ApiOperation("短信验证码登录")
    public Result phoneCodeLogin(@RequestBody LoginForm loginForm) {
        return Result.success();
    }
}
