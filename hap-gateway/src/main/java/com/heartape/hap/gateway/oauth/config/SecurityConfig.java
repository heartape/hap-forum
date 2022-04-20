package com.heartape.hap.gateway.oauth.config;

import com.heartape.hap.gateway.oauth.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.LinkedList;

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
    @Autowired
    private PreLoginHandler preLoginHandler;

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

        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder());
        managers.add(authenticationManager);
        managers.add(hapReactiveAuthenticationManager);
        return new DelegatingReactiveAuthenticationManager(managers);
    }

    /**
     * 在前面的url优先级更高
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .pathMatchers("/login", "/login/mail", "/login/phone", "/api/oauth/check", "/api/register/**")
                .permitAll()
                .pathMatchers("/api/oauth/token","/api/oauth/uid").denyAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyExchange().authenticated()
                .and()
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
                // todo:前后端对接时将验证码过滤器加入
                // .addFilterAt(preLoginHandler, SecurityWebFiltersOrder.FIRST)
                .build();
    }

}
