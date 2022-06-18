package com.heartape.hap.handler;

import com.alibaba.fastjson.JSONObject;
import com.heartape.hap.response.ErrorResult;
import com.heartape.hap.response.ResultCode;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 在获取ServerSecurityContextRepository中的token失败后转入认证流程
 * 默认实现RedirectServerAuthenticationEntryPoint将重定向至/login登录,可以自定义重定向的路径
 * 我这里前后端分离直接转为失败流程,由前端跳转
 */
@Component
public class HapAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {
    @Override
    public Mono<Void> commence(ServerWebExchange serverWebExchange, AuthenticationException e) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        String path = request.getPath().toString();
        return Mono.defer(() -> Mono.just(serverWebExchange.getResponse())).flatMap(response -> {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            // todo:修改http状态码
            ErrorResult result = ErrorResult.error(ResultCode.RESOURCE_NOT_EXISTED,path);
            DataBuffer buffer = dataBufferFactory.wrap(JSONObject.toJSONString(result).getBytes());
            return response.writeWith(Mono.just(buffer));
        });
    }
}
