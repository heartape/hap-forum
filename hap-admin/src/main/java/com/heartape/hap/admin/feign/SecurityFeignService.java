package com.heartape.hap.admin.feign;

import com.heartape.hap.admin.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("hap-security")
public interface SecurityFeignService {
    @GetMapping("/security/info")
    Result getInfo();
}
