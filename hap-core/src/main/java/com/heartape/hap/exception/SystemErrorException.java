package com.heartape.hap.exception;

import com.heartape.hap.constant.BusinessExceptionEnum;
import lombok.Getter;

@Getter
public class SystemErrorException extends BusinessException {

    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.SYSTEM_INNER_ERROR;

    public SystemErrorException(String message) {
        super(message);
    }
}
