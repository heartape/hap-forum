package com.heartape.hap.handler;

import com.alibaba.fastjson.JSONObject;
import com.heartape.hap.response.ErrorResult;
import com.heartape.hap.response.ResultCode;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 鉴权失败处理器
 */
@Component
public class HapAccessDeniedHandler implements ServerAccessDeniedHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, AccessDeniedException e) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        String path = request.getPath().toString();
        return Mono.defer(() -> Mono.just(serverWebExchange.getResponse()))
                .flatMap(response -> {
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    DataBufferFactory dataBufferFactory = response.bufferFactory();
                    // todo:修改http状态码
                    ErrorResult result = ErrorResult.error(ResultCode.PERMISSION_NO_ACCESS,path);
                    DataBuffer buffer = dataBufferFactory.wrap(JSONObject.toJSONString(result).getBytes());
                    // 解决中文乱码
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    return response.writeWith(Mono.just(buffer));
                });
    }
}
