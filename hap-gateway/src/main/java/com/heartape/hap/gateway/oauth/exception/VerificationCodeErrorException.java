package com.heartape.hap.gateway.oauth.exception;

import lombok.Getter;

@Getter
public class VerificationCodeErrorException extends BusinessException {
    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.USER_LOGIN_VERIFICATION_CODE_ERROR;

    public VerificationCodeErrorException(String message) {
        super(message);
    }
}