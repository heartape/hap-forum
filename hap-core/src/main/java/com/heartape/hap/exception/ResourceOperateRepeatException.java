package com.heartape.hap.exception;

import com.heartape.hap.constant.BusinessExceptionEnum;
import lombok.Getter;

@Getter
public class ResourceOperateRepeatException extends BusinessException {

    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.RESOURCE_OPERATE_REPEAT;

    public ResourceOperateRepeatException(String message) {
        super(message);
    }
}
