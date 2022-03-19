package com.heartape.hap.business.exception;

import lombok.Getter;

@Getter
public class RelyDataNotExistedException extends BusinessException {

    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.RELY_DATA_NOT_EXISTED;

    public RelyDataNotExistedException(String message) {
        super(message);
    }
}
