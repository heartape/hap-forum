package com.heartape.hap.controller;

import com.heartape.hap.entity.LoginInfo;
import com.heartape.hap.feign.OauthFeign;
import com.heartape.hap.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    @Lazy
    private OauthFeign oauthFeign;

    @PostMapping("/mail")
    public Mono<Result> mailCodeLogin(@RequestBody LoginInfo loginInfo) {
        Result oauthResult = oauthFeign.mailCodeLogin(loginInfo);
        Boolean data = (Boolean) oauthResult.getData();

        Result success = Result.success();
        return Mono.just(success);
    }

    @PostMapping("/phone")
    public Mono<Result> phoneCodeLogin(@RequestBody LoginInfo loginInfo) {
        Result success = Result.success();
        return Mono.just(success);
    }
}
