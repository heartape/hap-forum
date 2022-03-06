package com.heartape.hap.oauth.exception;

import lombok.Getter;

@Getter
public class SystemErrorException extends BusinessException {

    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.SYSTEM_INNER_ERROR;

    public SystemErrorException(String message) {
        super(message);
    }
}
