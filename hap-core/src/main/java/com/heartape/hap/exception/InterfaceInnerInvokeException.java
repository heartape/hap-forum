package com.heartape.hap.exception;

import lombok.Getter;

@Getter
public class InterfaceInnerInvokeException extends BusinessException {

    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.INTERFACE_INNER_INVOKE_ERROR;

    public InterfaceInnerInvokeException(String message) {
        super(message);
    }
}
