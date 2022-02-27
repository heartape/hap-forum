package com.heartape.hap.api.controller;

import com.heartape.hap.api.entity.RegisterInfo;
import com.heartape.hap.api.response.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/register")
@Validated
public class RegisterController {

    @GetMapping("/check")
    public Result check(@NotEmpty @RequestParam String username) {
        // 检查用户名是否存在
        return Result.success();
    }

    @PostMapping
    public Result register(@Valid @RequestBody RegisterInfo registerInfo) {
        // 检查用户名是否存在
        // 进行注册
        return Result.success();
    }
}
