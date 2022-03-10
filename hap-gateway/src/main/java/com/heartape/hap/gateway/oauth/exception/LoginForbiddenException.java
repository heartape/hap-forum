package com.heartape.hap.gateway.oauth.exception;

import lombok.Getter;

@Getter
public class LoginForbiddenException extends BusinessException {

    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.USER_LOGIN_FORBIDDEN;

    public LoginForbiddenException(String message) {
        super(message);
    }
}
