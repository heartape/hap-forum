package com.heartape.hap.exception;

import com.heartape.hap.constant.BusinessExceptionEnum;
import lombok.Getter;

@Getter
public class ResourceNotExistedException extends BusinessException {

    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.RESOURCE_NOT_EXISTED;

    public ResourceNotExistedException(String message) {
        super(message);
    }
}
