package com.heartape.hap.handler;

import com.google.gson.Gson;
import com.heartape.hap.response.ErrorResult;
import com.heartape.hap.response.ResultCode;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 未认证处理器
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
            ErrorResult result = ErrorResult.error(HttpStatus.FORBIDDEN, ResultCode.USER_TOKEN_ERROR,path);
            DataBuffer buffer = dataBufferFactory.wrap(new Gson().toJson(result).getBytes());
            return response.writeWith(Mono.just(buffer));
        });
    }
}
