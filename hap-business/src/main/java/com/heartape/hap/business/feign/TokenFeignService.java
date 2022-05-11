package com.heartape.hap.business.feign;

import com.heartape.hap.business.constant.HttpConstant;
import com.heartape.hap.business.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("hap-api")
public interface TokenFeignService {

    @GetMapping("/api/oauth/uid")
    Result getUid(@RequestHeader(HttpConstant.HEADER_TOKEN) String token);

    @GetMapping("/api/creator/info/nickname")
    Result getNickname(@RequestParam Long uid);

    @GetMapping("/api/oauth/token")
    Result getTokenInfo(@RequestHeader(HttpConstant.HEADER_TOKEN) String token);
}
