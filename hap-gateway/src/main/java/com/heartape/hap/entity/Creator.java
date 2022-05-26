package com.heartape.hap.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 系统用户
 */
@Data
public class Creator {

    /**用户id*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    /**邮箱*/
    private String email;

    /**用户手机号*/
    private String mobile;

    /**用户密码*/
    private String password;

    /**用户昵称*/
    private String nickname;

    /**用户昵称*/
    private String profile;

    /**用户头像*/
    private String avatar;

    /**用户角色*/
    private String role;

    /**账户状态*/
    private String accountStatus;

}
