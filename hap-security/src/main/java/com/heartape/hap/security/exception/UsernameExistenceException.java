package com.heartape.hap.security.exception;

import lombok.Getter;

/**
 * 注册时用户名已存在
 */
@Getter
public class UsernameExistenceException extends BusinessException {

    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.USER_HAS_EXISTED;

    public UsernameExistenceException() {
        super();
    }
}
