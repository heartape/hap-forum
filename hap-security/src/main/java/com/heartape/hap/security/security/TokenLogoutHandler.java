package com.heartape.hap.security.security;

import com.heartape.hap.security.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenLogoutHandler implements LogoutSuccessHandler {
    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        tokenUtil.delete(httpServletRequest);
    }
}
