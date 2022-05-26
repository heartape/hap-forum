package com.heartape.hap.handler;

import com.alibaba.fastjson.JSONObject;
import com.heartape.hap.entity.HapUserDetails;
import com.heartape.hap.exception.SystemErrorException;
import com.heartape.hap.feign.OauthFeign;
import com.heartape.hap.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * 认证处理器，可以作为token认证和登录认证入口;
 * AuthenticationManager的逻辑可以实现多种认证方式的并存等能力
 * 在作为自定义的登录入口时,如果使用@Primary注解，会启用该处理器定为登录处理器
 */
@Slf4j
@Component
@Primary
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
            throw new SystemErrorException("调用获取token信息接口异常");
        }
        Object data = result.getData();
        HapUserDetails userDetails = JSONObject.parseObject(JSONObject.toJSONString(data), HapUserDetails.class);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return Mono.just(new UsernamePasswordAuthenticationToken(userDetails, null, authorities));
    }
}
