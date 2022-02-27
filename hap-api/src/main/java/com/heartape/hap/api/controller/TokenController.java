package com.heartape.hap.api.controller;

import com.heartape.hap.api.entity.LoginCode;
import com.heartape.hap.api.entity.Visitor;
import com.heartape.hap.api.entity.VisitorInfo;
import com.heartape.hap.oauth.entity.HapUserDetails;
import com.heartape.hap.api.response.Result;
import com.heartape.hap.api.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/oauth")
public class TokenController {

    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping("/code")
    public Result getCode() {
        LoginCode loginCode = tokenUtils.newCode();
        return Result.success(loginCode);
    }

    @PostMapping("/code")
    public Result checkCode(@RequestBody LoginCode loginCode) {
        boolean check = tokenUtils.checkCode(loginCode);
        return Result.success(check);
    }

    @GetMapping("/uid")
    public Result uid() {
        long uid = tokenUtils.getUid();
        return Result.success(uid);
    }

    @GetMapping("/token")
    public Result token() {
        HapUserDetails info = tokenUtils.getUserDetails();
        return Result.success(info);
    }

    @GetMapping("/info")
    public Result getInfo() {
        HapUserDetails userDetails = tokenUtils.getUserDetails();
        VisitorInfo visitorInfo = new VisitorInfo();
        BeanUtils.copyProperties(userDetails,visitorInfo);
        return Result.success(visitorInfo);
    }

    /**
     * 用于gateway登陆时调用数据库查询用户信息
     */
    @GetMapping("/login")
    public Result login(String username) {
        // 123456
        Visitor visitor = new Visitor(1L,"123456", "{bcrypt}$2a$10$LqjI9U/GxgHn/ws9bOMCNOQkjBRm7GYA1w/BI31nqQmrVoFrLLgsu","nickname","avatar","admin","1234567890","12345@qq.com", LocalDateTime.now());
        return Result.success(visitor);
    }
}
