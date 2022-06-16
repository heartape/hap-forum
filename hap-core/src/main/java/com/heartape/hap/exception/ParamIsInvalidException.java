package com.heartape.hap.exception;

import com.heartape.hap.constant.BusinessExceptionEnum;
import lombok.Getter;

@Getter
public class ParamIsInvalidException extends BusinessException {

    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.PARAM_IS_INVALID;

    public ParamIsInvalidException(String message) {
        super(message);
    }
}
