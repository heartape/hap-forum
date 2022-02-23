package com.heartape.hap.oauth.controller;

import com.heartape.hap.oauth.entity.HapUserDetails;
import com.heartape.hap.oauth.entity.LoginInfo;
import com.heartape.hap.oauth.entity.VisitorInfo;
import com.heartape.hap.oauth.handler.HapReactiveAuthenticationManager;
import com.heartape.hap.oauth.response.Result;
import com.heartape.hap.oauth.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class SecurityController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ReactiveAuthenticationManager authenticationManager;

    @GetMapping("/info")
    public Mono<Result> getInfo(ServerWebExchange exchange){
        HapUserDetails userDetails = tokenUtils.getInfo(exchange);
        VisitorInfo visitorInfo = new VisitorInfo();
        BeanUtils.copyProperties(userDetails,visitorInfo);
        return Mono.just(Result.success(visitorInfo));
    }

    @PostMapping("/check")
    public Mono<Result> checkRegister(@RequestBody String username){
        return Mono.just(Result.success(true));
    }

    @PostMapping("/login")
    public Mono<Result> login(@RequestBody LoginInfo loginInfo){
        // 登录账号密码
        String username = loginInfo.getUsername();
        String password = loginInfo.getPassword();
        // 登陆验证
        Mono<Authentication> authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return authenticate.map(authentication -> {
            HapUserDetails userDetails = (HapUserDetails) authentication.getPrincipal();
            return Result.success(tokenUtils.create(userDetails));
        });
    }

}
