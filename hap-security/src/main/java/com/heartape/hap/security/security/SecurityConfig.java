package com.heartape.hap.security.security;

import com.google.gson.Gson;
import com.heartape.hap.security.response.ErrorResult;
import com.heartape.hap.security.response.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * prePostEnabled属性不添加会导致无法启动
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private VisitorDetailsServiceImpl userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private TokenLogoutHandler tokenLogoutHandler;
    @Autowired
    private LoginFilter loginFilter;

    // 重写该方法解决无法注入的问题
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问,登陆无法访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // CSRF禁用，因为不使用session
                .csrf().disable()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint((request, response, e) -> {
                    String path = request.getRequestURI();
                    ErrorResult result = ErrorResult.error(HttpStatus.FORBIDDEN, ResultCode.USER_TOKEN_ERROR,path);
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().write(new Gson().toJson(result));
                }).and()
                .authorizeRequests()
                .antMatchers("/swagger-ui.html",
                        "/v2/api-docs", // swagger api json
                        "/swagger-resources/configuration/ui", // 用来获取支持的动作
                        "/swagger-resources", // 用来获取api-docs的URI
                        "/swagger-resources/configuration/security", // 安全选项
                        "/swagger-resources/**",
                        //补充路径，近期在搭建swagger接口文档时，通过浏览器控制台发现该/webjars路径下的文件被拦截，故加上此过滤条件即可。(2020-10-23)
                        "/webjars/**").permitAll()
                .antMatchers("/security/forbidden").hasRole("admin")
                .antMatchers("/security/register").anonymous()
                .antMatchers("/security/login").anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        http.logout().logoutUrl("/security/logout").logoutSuccessHandler(tokenLogoutHandler);

        http.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
