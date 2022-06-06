package com.heartape.hap.exception;

import com.heartape.hap.constant.BusinessExceptionEnum;
import lombok.Getter;

@Getter
public class RegisterUserExistedException extends BusinessException {

    private final BusinessExceptionEnum exceptionEnum = BusinessExceptionEnum.REGISTER_USER_EXISTED;

    public RegisterUserExistedException(String message) {
        super(message);
    }
}
