package com.heartape.hap.oauth.feign;

import com.heartape.hap.oauth.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient("hap-api")
public interface LoginFeign {
    @GetMapping("/api/oauth/login")
    Result login(@RequestParam String username);
}
