package com.heartape.hap.business.feign;

import com.heartape.hap.business.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("hap-api")
public interface TokenFeignService {
    @GetMapping("/api/oauth/uid")
    Result getUid();
}
