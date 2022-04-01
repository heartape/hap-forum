package com.heartape.hap.api.controller;

import com.heartape.hap.api.entity.*;
import com.heartape.hap.api.entity.RO.LoginCodeRO;
import com.heartape.hap.api.response.Result;
import com.heartape.hap.api.utils.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oauth")
@Api("认证相关接口")
public class TokenController {

    @Autowired
    private TokenUtils tokenUtils;

    @ApiOperation("获取验证码")
    @GetMapping("/code")
    public Result getCode() {
        LoginCode loginCode = tokenUtils.newCode();
        return Result.success(loginCode);
    }

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
    public Result getInfo() {
        HapUserDetails userDetails = tokenUtils.getUserDetails();
        CreatorInfo creatorInfo = new CreatorInfo();
        BeanUtils.copyProperties(userDetails, creatorInfo);
        return Result.success(creatorInfo);
    }

    @PostMapping("/check")
    public Result check() {
        return Result.success("success");
    }

    /**
     * 用于gateway登陆时调用数据库查询用户信息
     */
    @GetMapping("/login/mail/password")
    public Result mailPasswordLogin(String email) {
        if (!"heartape@163.com".equals(email)) {
            throw new RuntimeException();
        }
        // 123456
        Creator creator = new Creator(1L,"heartape@163.com","1234567890", "$2a$10$RlGjkJAbNDAXYf0VTE4P5.wbwb42KLFE8.Br7jA.gSMSCCkCGgZM2","加藤惠", "路人女主","https://file.heartape.com/picture/avatar-1.jpg","admin");
        return Result.success(creator);
    }

    @PostMapping("/login/mail/code")
    public Result mailCodeLogin(@RequestBody LoginForm loginForm) {
        return Result.success();
    }

    @PostMapping("/login/phone/code")
    public Result phoneCodeLogin(@RequestBody LoginForm loginForm) {
        return Result.success();
    }
}
