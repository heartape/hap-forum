package com.heartape.hap.api.controller;

import com.heartape.hap.oauth.security.HapUserDetails;
import com.heartape.hap.api.response.Result;
import com.heartape.hap.api.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    @Autowired
    private TokenUtils tokenUtils;

    @GetMapping("/uid")
    public Result uid() {
        long uid = tokenUtils.getUid();
        return Result.success(uid);
    }

    @GetMapping
    public Result token() {
        HapUserDetails info = tokenUtils.getInfo();
        return Result.success(info);
    }
}
