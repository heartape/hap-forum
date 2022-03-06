package com.heartape.hap.oauth.feign;

import com.heartape.hap.oauth.entity.LoginCode;
import com.heartape.hap.oauth.entity.LoginInfo;
import com.heartape.hap.oauth.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("hap-api")
public interface OauthFeign {

    @GetMapping("/api/oauth/login/mail/password")
    Result mailPasswordLogin(@RequestParam String username);

    @PostMapping("/api/oauth/login/mail/code")
    Result mailCodeLogin(@RequestBody LoginInfo loginInfo);

    @PostMapping("/api/oauth/login/phone/code")
    Result phoneCodeLogin(@RequestBody LoginInfo loginInfo);

    @GetMapping("/api/oauth/code")
    Result getCode();

    @PostMapping("/api/oauth/code")
    Result checkCode(@RequestBody LoginCode loginCode);
}
