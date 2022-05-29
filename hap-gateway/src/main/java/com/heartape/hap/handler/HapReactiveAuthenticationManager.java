package com.heartape.hap.handler;

import com.alibaba.fastjson.JSONObject;
import com.heartape.hap.entity.HapUserDetails;
import com.heartape.hap.feign.OauthFeign;
import com.heartape.hap.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * 当前实现类token认证处理器,因为并非登录认证,而是对已登录账号进行检查,所以并不会加入到管理器链中,只在ServerSecurityContextRepository调用
 * 不同的登录认证方式需要不同的ReactiveAuthenticationManager实现类
 */
@Slf4j
@Component
public class HapReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    @Lazy
    private OauthFeign oauthFeign;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String principal = authentication.getPrincipal().toString();
        CompletableFuture<Result> future = CompletableFuture.supplyAsync(() -> oauthFeign.token(principal));
        Result result = future.join();
        if (result.getCode() != 1) {
            String message = result.getMessage();
            log.info("调用token认证接口认证失败:{}", message);
            return Mono.empty();
        }
        Object data = result.getData();
        if (data == null) {
            return Mono.empty();
        }
        HapUserDetails userDetails = JSONObject.parseObject(JSONObject.toJSONString(data), HapUserDetails.class);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return Mono.just(new UsernamePasswordAuthenticationToken(userDetails, null, authorities));
    }
}
