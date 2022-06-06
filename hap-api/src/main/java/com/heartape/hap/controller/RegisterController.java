package com.heartape.hap.controller;

import com.heartape.hap.entity.dto.CreatorPhoneDTO;
import com.heartape.hap.entity.dto.CreatorEmailDTO;
import com.heartape.hap.entity.ro.CreatorEmailRO;
import com.heartape.hap.entity.ro.CreatorPhoneRO;
import com.heartape.hap.response.Result;
import com.heartape.hap.service.IRegisterService;
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

    @GetMapping("/email")
    @ApiOperation("获取注册邮箱验证码")
    public Result checkEmail(@Length(min = 6, max = 32) @RequestParam String email) {
        registerService.checkEmail(email);
        return Result.success();
    }

    @GetMapping("/phone")
    @ApiOperation("获取注册短信验证码")
    public Result checkPhone(@NotEmpty @RequestParam String mobile) {
        registerService.checkPhone(mobile);
        return Result.success();
    }

    @PostMapping("/email")
    @ApiOperation("邮箱注册")
    public Result registerEmail(@Valid @RequestBody CreatorEmailRO creatorEmailRO) {
        CreatorEmailDTO creatorDTO = new CreatorEmailDTO();
        BeanUtils.copyProperties(creatorEmailRO, creatorDTO);
        registerService.registerEmail(creatorDTO);
        return Result.success();
    }

    @PostMapping("/phone")
    @ApiOperation("手机注册")
    public Result registerPhone(@Valid @RequestBody CreatorPhoneRO creatorPhoneRO) {
        CreatorPhoneDTO creatorPhoneDTO = new CreatorPhoneDTO();
        BeanUtils.copyProperties(creatorPhoneRO, creatorPhoneDTO);
        registerService.registerPhone(creatorPhoneDTO);
        return Result.success();
    }
}
