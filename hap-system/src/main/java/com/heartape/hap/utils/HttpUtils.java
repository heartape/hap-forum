package com.heartape.hap.utils;

import com.heartape.hap.constant.HttpConstant;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class HttpUtils {

    /**
     * 获取request
     */
    public HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new RuntimeException();
        }
        return attributes.getRequest();
    }

    public String getToken(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(HttpConstant.HEADER_TOKEN);
    }
}
