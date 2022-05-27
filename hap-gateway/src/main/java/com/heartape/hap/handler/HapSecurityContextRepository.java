package com.heartape.hap.handler;

import com.heartape.hap.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 存储认证授权的相关信息，根据url来选择不同的ReactiveAuthenticationManager实现类即不同的认证方式
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
        String path = exchange.getRequest().getPath().toString();
        // 登陆时不需要验证token
        if ("/login".equals(path)) {
            return Mono.empty();
        }
        String token = tokenUtils.getToken(exchange);
        if (!StringUtils.hasText(token)) {
            throw new DisabledException("令牌不存在");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(token, null);
        return authenticationManager.authenticate(authentication).map(SecurityContextImpl::new);
    }
}
