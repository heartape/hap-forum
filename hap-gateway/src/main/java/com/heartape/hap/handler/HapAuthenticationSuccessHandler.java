package com.heartape.hap.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.heartape.hap.entity.HapUserDetails;
import com.heartape.hap.exception.SystemErrorException;
import com.heartape.hap.feign.OauthFeign;
import com.heartape.hap.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * formLogin登录成功处理器
 */
@Component
public class HapAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Autowired
    @Lazy
    private OauthFeign oauthFeign;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        return Mono.defer(() -> Mono.just(webFilterExchange.getExchange().getResponse()).flatMap(response -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            // 生成JWT token
            HapUserDetails userDetails = (HapUserDetails) authentication.getPrincipal();
            CompletableFuture<Result> future = CompletableFuture.supplyAsync(() -> oauthFeign.createToken(userDetails));
            Result data = future.join();
            if (data.getCode() != 1) {
                return Mono.error(new SystemErrorException("请求创建token接口异常"));
            }
            String token = (String) data.getData();
            Result result = Result.success(token);
            DataBuffer dataBuffer = dataBufferFactory.wrap(JSONObject.toJSONString(result).getBytes());
            return response.writeWith(Mono.just(dataBuffer));
        }));
    }
}
