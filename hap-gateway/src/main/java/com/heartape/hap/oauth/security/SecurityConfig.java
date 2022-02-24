package com.heartape.hap.oauth.security;

import com.heartape.hap.oauth.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.LinkedList;

/**
 * prePostEnabled属性不添加会导致无法启动
 */
@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private HapDetailsService userDetailsService;
    @Autowired
    private HapAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private HapAuthenticationEntryPoint entryPoint;
    @Autowired
    private HapReactiveAuthenticationManager hapReactiveAuthenticationManager;
    @Autowired
    private HapSecurityContextRepository hapSecurityContextRepository;
    @Autowired
    private HapAuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private HapAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private HapLogoutSuccessHandler logoutSuccessHandler;

    /**
     * BCrypt密码编码
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 注册用户信息验证管理器，可按需求添加多个按顺序执行
     */
    @Bean
    public ReactiveAuthenticationManager authenticationManager() {
        LinkedList<ReactiveAuthenticationManager> managers = new LinkedList<>();
        // 必须放最后不然会优先使用用户名密码校验但是用户名密码不对时此 AuthenticationManager 会调用 Mono.error 造成后面的 AuthenticationManager 不生效
        managers.add(new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService));
        managers.add(hapReactiveAuthenticationManager);
        return new DelegatingReactiveAuthenticationManager(managers);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        // @formatter:off
        return http
                .authorizeExchange()
                .pathMatchers("/login").permitAll()
                .pathMatchers("/check").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyExchange().authenticated().and()
                .formLogin()
                .authenticationSuccessHandler(authenticationSuccessHandler)
                .authenticationFailureHandler(authenticationFailureHandler)
                .and()
                // CSRF禁用，因为不使用session
                .csrf().disable()
                // 认证处理器
                .authenticationManager(authenticationManager())
                .securityContextRepository(hapSecurityContextRepository)
                // 认证失败处理器
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                .exceptionHandling().authenticationEntryPoint(entryPoint).and()
                // 退出登录处理器
                .logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler).and()
                .build();
        // @formatter:on
    }

}