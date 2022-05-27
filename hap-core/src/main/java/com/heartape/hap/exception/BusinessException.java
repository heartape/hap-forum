package com.heartape.hap.exception;

import com.heartape.hap.constant.BusinessExceptionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    private BusinessExceptionEnum exceptionEnum;

    public BusinessException(String message) {
        super(message);
    }

}
