package com.heartape.hap.oauth.handler;

import com.heartape.hap.oauth.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 存储认证授权的相关信息
 */
@Component
public class HapSecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    private HapReactiveAuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 保存身份信息
     */
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    /**
     * 获取身份信息
     */
    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = tokenUtils.getToken(exchange);
        if (StringUtils.hasText(token)) {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(token, null)
            ).map(SecurityContextImpl::new);
        }
        return Mono.empty();
    }
}
