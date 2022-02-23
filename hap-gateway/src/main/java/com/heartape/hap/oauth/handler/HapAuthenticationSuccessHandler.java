package com.heartape.hap.oauth.handler;

import com.heartape.hap.oauth.entity.HapUserDetails;
import com.heartape.hap.oauth.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 登录成功处理器
 */
@Component
public class HapAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        return Mono.defer(() -> Mono.just(webFilterExchange.getExchange().getResponse()).flatMap(response -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            // 生成JWT token
            HapUserDetails userDetails = (HapUserDetails) authentication.getPrincipal();
            String token = tokenUtils.create(userDetails);
            DataBuffer dataBuffer = dataBufferFactory.wrap(token.getBytes());
            return response.writeWith(Mono.just(dataBuffer));
        }));
    }
}
