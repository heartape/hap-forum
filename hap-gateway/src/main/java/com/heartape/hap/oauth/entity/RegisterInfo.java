package com.heartape.hap.oauth.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 注册信息对象
 */
@Data
public class RegisterInfo {

    @Length(min = 8, max = 16, message = "请输入8-16位长度的用户名")
    private String username;

    @Length(min = 8, max = 16, message = "请输入8-16位长度的密码")
    private String password;

    @Length(min = 2, max = 16, message = "请输入2-16位长度的昵称")
    private String nickname;

    @Length(min = 5, max = 10, message = "请输入正确的角色")
    private String role;

    private String mobile;

    /**验证码*/
    private String code;
}
