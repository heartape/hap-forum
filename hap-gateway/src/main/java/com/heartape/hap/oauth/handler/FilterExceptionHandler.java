package com.heartape.hap.oauth.handler;

import com.google.gson.Gson;
import com.heartape.hap.oauth.exception.LoginForbiddenException;
import com.heartape.hap.oauth.response.ErrorResult;
import com.heartape.hap.oauth.response.ResultCode;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 过滤器异常处理
 */
@Component
@Slf4j
public class FilterExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.error("exception:{},\ncaused by:{}", ex.getClass(), ex.getMessage());
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();
        ResultCode resultCode;
        if (ex instanceof SignatureException) {
            resultCode = ResultCode.USER_TOKEN_ERROR;
        } else if (ex instanceof LoginForbiddenException) {
            resultCode = ResultCode.USER_LOGIN_ERROR;
        }
        else {
            resultCode = ResultCode.PERMISSION_NO_ACCESS;
        }
        return Mono.defer(() -> Mono.just(exchange.getResponse()))
                .flatMap(response -> {
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    DataBufferFactory dataBufferFactory = response.bufferFactory();
                    ErrorResult result = ErrorResult.error(HttpStatus.FORBIDDEN, resultCode,path);
                    DataBuffer buffer = dataBufferFactory.wrap(new Gson().toJson(result).getBytes());
                    return response.writeWith(Mono.just(buffer));
                });
    }
}
