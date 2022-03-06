package com.heartape.hap.api.controller;

import com.heartape.hap.api.entity.RegisterInfo;
import com.heartape.hap.api.response.Result;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/register")
@Validated
public class RegisterController {

    @GetMapping("/check/email")
    public Result checkEmail(@Length(min = 8) @RequestParam String email) {
        // 检查邮箱是否存在
        return Result.success();
    }

    @GetMapping("/check/phone")
    public Result checkPhone(@NotEmpty @RequestParam String phone) {
        // 检查手机号是否存在
        return Result.success();
    }

    @PostMapping
    public Result register(@Valid @RequestBody RegisterInfo registerInfo) {
        // 检查注册信息
        // 进行注册
        return Result.success(registerInfo);
    }
}
