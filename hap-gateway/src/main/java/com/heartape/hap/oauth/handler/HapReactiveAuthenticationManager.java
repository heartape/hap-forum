package com.heartape.hap.oauth.handler;

import com.heartape.hap.oauth.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collection;

/**
 * 认证处理器，可以作为token认证和登录认证入口;
 * 如果使用@Primary注解，会启用该处理器定为登录处理器
 */
@Slf4j
@Component
//@Primary
public class HapReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> tokenUtils.parseAutograph(auth.getPrincipal().toString()))
                .map(claims -> {
                    String tokenKey = tokenUtils.getTokenKey(claims);
                    UserDetails userDetails = tokenUtils.getTokenValue(tokenKey);
                    Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
                    return new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities
                    );
                });
    }
}
