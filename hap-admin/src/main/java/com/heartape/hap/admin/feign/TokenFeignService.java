package com.heartape.hap.admin.feign;

import com.heartape.hap.admin.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("hap-api")
public interface TokenFeignService {
    @GetMapping("/api/oauth/uid")
    Result getUid();
}
