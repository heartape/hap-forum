package com.heartape.hap.handler;

import com.heartape.hap.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 存储和获取认证授权的相关信息，
 * 在使用token的情况下改造为token认证入口
 */
@Component
@Slf4j
public class HapSecurityContextRepository implements ServerSecurityContextRepository {

    @Autowired
    private HapReactiveAuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 保存身份信息,非session存储登录信息不用实现
     */
    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    /**
     * 获取身份信息,使用token进行验证,因为spring security默认所有请求都会检查session包括登录,所以登陆也会进入先该方法,再进行登录;
     * 但是目前没有使用session,所以就算登录时先认证token成功了,还是会继续登录流程,所以通过定义多条过滤器链跳过对登录的token验证
     */
    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        String token = tokenUtils.getToken(exchange);
        if (!StringUtils.hasText(token)) {
            // 不能在此处抛异常,因为会导致白名单请求被中断
            // log.info("令牌不存在");
            return Mono.empty();
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(token, null);
        return authenticationManager.authenticate(authentication).map(SecurityContextImpl::new);
    }
}
