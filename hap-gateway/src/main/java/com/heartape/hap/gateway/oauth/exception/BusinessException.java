package com.heartape.hap.gateway.oauth.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    private BusinessExceptionEnum exceptionEnum;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException() {
    }

    public BusinessException(BusinessExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }
}
