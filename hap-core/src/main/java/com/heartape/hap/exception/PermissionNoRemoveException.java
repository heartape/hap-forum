package com.heartape.hap.exception;

import com.heartape.hap.constant.BusinessExceptionEnum;
import lombok.Getter;

@Getter
public class PermissionNoRemoveException extends BusinessException {

    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.PERMISSION_NO_REMOVE;

    public PermissionNoRemoveException(String message) {
        super(message);
    }
}
