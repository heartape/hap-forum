package com.heartape.hap.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 登录信息对象
 */
@Data
public class LoginInfo {

    private String username;

    /**验证码*/
    private String code;
}
