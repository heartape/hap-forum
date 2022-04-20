package com.heartape.hap.api.controller;

import com.heartape.hap.api.entity.dto.CreatorDTO;
import com.heartape.hap.api.entity.ro.CreatorRO;
import com.heartape.hap.api.response.Result;
import com.heartape.hap.api.service.IRegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/register")
@Api(tags = "注册")
@Validated
public class RegisterController {

    @Autowired
    private IRegisterService registerService;

    @GetMapping("/check/email")
    @ApiOperation("获取注册邮箱验证码")
    public Result checkEmail(@Length(min = 8) @RequestParam String email) {
        registerService.checkEmail(email);
        return Result.success();
    }

    @GetMapping("/check/phone")
    @ApiOperation("获取注册短信验证码")
    public Result checkPhone(@NotEmpty @RequestParam String mobile) {
        registerService.checkPhone(mobile);
        return Result.success();
    }

    @PostMapping("/creator")
    @ApiOperation("创作者自注册")
    public Result register(@Valid @RequestBody CreatorRO creatorRO) {
        CreatorDTO creatorDTO = new CreatorDTO();
        BeanUtils.copyProperties(creatorRO, creatorDTO);
        registerService.register(creatorDTO);
        return Result.success();
    }
}
