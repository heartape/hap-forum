package com.heartape.hap.business.utils;

import com.heartape.hap.business.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class AssertUtils {

    /**
     * 业务断言
     * @param expression 期望为true
     */
    public void businessState(boolean expression, BusinessException e) {
        if (!expression) {
            throw e;
        }
    }
}
