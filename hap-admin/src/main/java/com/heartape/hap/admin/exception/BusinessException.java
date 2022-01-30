package com.heartape.hap.admin.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private BusinessExceptionEnum exceptionEnum;

    public BusinessException() {
    }

    public BusinessException(BusinessExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }
}
