package com.heartape.hap.security.exception;

import lombok.Getter;

/**
 * 注册失败
 */
@Getter
public class RoleExistedException extends BusinessException {

    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.ROLE_NO_EXISTED;

    public RoleExistedException() {
        super();
    }
}
