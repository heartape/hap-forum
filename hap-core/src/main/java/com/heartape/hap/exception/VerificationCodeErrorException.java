package com.heartape.hap.exception;

import com.heartape.hap.constant.BusinessExceptionEnum;
import lombok.Getter;

@Getter
public class VerificationCodeErrorException extends BusinessException {
    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.USER_LOGIN_VERIFICATION_CODE_ERROR;

    public VerificationCodeErrorException(String message) {
        super(message);
    }
}
