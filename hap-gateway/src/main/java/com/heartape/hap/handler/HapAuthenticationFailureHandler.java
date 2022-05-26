package com.heartape.hap.handler;

import com.google.gson.Gson;
import com.heartape.hap.response.ErrorResult;
import com.heartape.hap.response.ResultCode;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * formLogin登录失败处理器
 */
@Component
public class HapAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException e) {
        ServerHttpRequest request = webFilterExchange.getExchange().getRequest();
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        String path = request.getPath().toString();
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        ErrorResult result = ErrorResult.error(HttpStatus.FORBIDDEN, ResultCode.USER_LOGIN_ERROR,path);
        DataBuffer dataBuffer = dataBufferFactory.wrap(new Gson().toJson(result).getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }
}
