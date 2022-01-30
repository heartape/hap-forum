package com.heartape.hap.security.controller;

import com.heartape.hap.security.entity.LoginUserToken;
import com.heartape.hap.security.entity.VisitorInfo;
import com.heartape.hap.security.response.Result;
import com.heartape.hap.security.utils.TokenUtil;
import com.heartape.hap.security.service.IVisitorService;
import com.heartape.hap.security.entity.LoginInfo;
import com.heartape.hap.security.entity.RegisterInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/security")
@Api(tags = "security认证鉴权")
public class SecurityController {

    @Autowired
    private IVisitorService iVisitorService;

    @Autowired
    private TokenUtil tokenUtil;

    @GetMapping("/info")
    @ApiOperation("获取信息")
    public Result getInfo(){
        LoginUserToken info = tokenUtil.getInfo();
        VisitorInfo visitorInfo = new VisitorInfo();
        BeanUtils.copyProperties(info, visitorInfo);
        return Result.success(visitorInfo);
    }

    @PostMapping("/register/check")
    @ApiOperation("注册前用户名检查")
    @ApiImplicitParam(name = "username",value = "待检查用户名")
    public Result checkRegister(@Valid @RequestBody String username){
        iVisitorService.check(username);
        return Result.success();
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public Result register(@Valid @RequestBody RegisterInfo registerInfo){
        iVisitorService.register(registerInfo);
        return Result.success();
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result login(@Valid @RequestBody LoginInfo loginInfo){
        String token = iVisitorService.login(loginInfo);
        return Result.success(token);
    }

    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public Result logout(){
        iVisitorService.logout();
        return Result.success();
    }
}
