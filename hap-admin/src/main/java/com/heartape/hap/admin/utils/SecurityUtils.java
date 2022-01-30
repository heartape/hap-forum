package com.heartape.hap.admin.utils;

import com.heartape.hap.admin.entity.security.LoginUserToken;
import com.heartape.hap.admin.feign.SecurityFeignService;
import com.heartape.hap.admin.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author heartape
 * @description feign远程调用接口封装
 * @since 2021/8/14
 */
@Component
public class SecurityUtils {

    @Autowired
    private SecurityFeignService securityFeignService;

    /**
     * 调用远程接口获取用户信息并验证
     */
    private LoginUserToken securityFeignData(){
        // 远程调用获取用户信息
        Result result = securityFeignService.getInfo();
        return (LoginUserToken)result.getData();
    }

    /**
     * 调用远程接口获取用户id
     */
    public Long getUid(){
        return securityFeignData().getUid();
    }


}
