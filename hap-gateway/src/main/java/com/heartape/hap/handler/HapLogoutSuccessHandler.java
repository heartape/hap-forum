package com.heartape.hap.handler;

import com.google.gson.Gson;
import com.heartape.hap.response.Result;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class HapLogoutSuccessHandler implements ServerLogoutSuccessHandler {
    @Override
    public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
        return Mono.defer(() -> Mono.just(exchange.getExchange().getResponse()).flatMap(response -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            // todo:退出删除token
            Result result = Result.success();
            DataBuffer dataBuffer = dataBufferFactory.wrap(new Gson().toJson(result).getBytes());
            return response.writeWith(Mono.just(dataBuffer));
        }));
    }
}
