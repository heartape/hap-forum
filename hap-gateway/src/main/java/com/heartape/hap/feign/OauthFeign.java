package com.heartape.hap.feign;

import com.heartape.hap.constant.OauthConstant;
import com.heartape.hap.entity.HapUserDetails;
import com.heartape.hap.entity.LoginCode;
import com.heartape.hap.entity.LoginInfo;
import com.heartape.hap.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient("hap-api")
public interface OauthFeign {

    @GetMapping("/api/oauth/login/mail/password")
    Result mailPasswordLogin(@RequestParam String email);

    @PostMapping("/api/oauth/login/mail/code")
    Result mailCodeLogin(@RequestBody LoginInfo loginInfo);

    @PostMapping("/api/oauth/login/phone/code")
    Result phoneCodeLogin(@RequestBody LoginInfo loginInfo);

    /**
     * 获取验证码
     */
    @GetMapping("/api/oauth/code")
    Result getCode();

    /**
     * 校验验证码
     */
    @PostMapping("/api/oauth/code")
    Result checkCode(@RequestBody LoginCode loginCode);

    /**
     * 获取token信息
     */
    @GetMapping("/api/oauth/token")
    Result token(@RequestHeader(OauthConstant.HEADER_TOKEN) String token);

    /**
     * 创建token
     */
    @PostMapping("/api/oauth/token")
    Result createToken(@RequestBody HapUserDetails userDetails);
}
