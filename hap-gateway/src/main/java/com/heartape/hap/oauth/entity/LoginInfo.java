package com.heartape.hap.oauth.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 登录信息对象
 */
@Data
public class LoginInfo {

    @Length(min = 8, max = 16, message = "请输入8-16位长度的用户名")
    private String username;

    @Length(min = 8, max = 16, message = "请输入8-16位长度的密码")
    private String password;

    /**验证码*/
    private String code;

    /**唯一标识*/
    private String uuid;
}
