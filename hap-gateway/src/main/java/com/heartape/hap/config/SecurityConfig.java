package com.heartape.hap.config;

import com.heartape.hap.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Autowired
    private HapDetailsService userDetailsService;
    @Autowired
    private HapAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private HapAuthenticationEntryPoint entryPoint;
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
        UserDetailsRepositoryReactiveAuthenticationManager usernameAuthenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        usernameAuthenticationManager.setPasswordEncoder(passwordEncoder());
        managers.add(usernameAuthenticationManager);
        // 用于添加不同的认证方式
        // managers.add();
        return new DelegatingReactiveAuthenticationManager(managers);
    }

    /**
     * 处理器链配置
     * 可以将无需认证和禁止访问的请求地址配置到优先级更高的处理器链配置中,以避免多余的对token的认证操作
     */
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity http) {
        PathPatternParserServerWebExchangeMatcher loginMatcher = new PathPatternParserServerWebExchangeMatcher("/login");
        PathPatternParserServerWebExchangeMatcher loginsMatcher = new PathPatternParserServerWebExchangeMatcher("/login/**");
        PathPatternParserServerWebExchangeMatcher oauthMatcher = new PathPatternParserServerWebExchangeMatcher("/api/oauth/**");
        PathPatternParserServerWebExchangeMatcher apiLoginMatcher = new PathPatternParserServerWebExchangeMatcher("/api/login/**");
        PathPatternParserServerWebExchangeMatcher registerMatcher = new PathPatternParserServerWebExchangeMatcher("/api/register/**");
        List<ServerWebExchangeMatcher> matchers = new ArrayList<>();
        matchers.add(loginMatcher);
        matchers.add(loginsMatcher);
        matchers.add(oauthMatcher);
        matchers.add(apiLoginMatcher);
        matchers.add(registerMatcher);
        return http
                .securityMatcher(new OrServerWebExchangeMatcher(matchers))
                .authorizeExchange()
                .pathMatchers("/login", "/login/**", "/api/login/**", "/api/register/**")
                .permitAll()
                .pathMatchers("/api/oauth/**")
                .denyAll()
                .and()
                .csrf().disable()
                .formLogin()
                .authenticationSuccessHandler(authenticationSuccessHandler)
                .authenticationFailureHandler(authenticationFailureHandler)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
                .exceptionHandling().authenticationEntryPoint(entryPoint).and()
                .build();
    }

    /**
     * 主处理器链配置
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
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
