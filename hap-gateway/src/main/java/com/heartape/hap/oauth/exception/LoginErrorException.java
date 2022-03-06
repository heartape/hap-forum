package com.heartape.hap.oauth.exception;

import lombok.Getter;

@Getter
public class LoginErrorException extends BusinessException {

    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.USER_LOGIN_ERROR;

    public LoginErrorException(String message) {
        super(message);
    }
}
