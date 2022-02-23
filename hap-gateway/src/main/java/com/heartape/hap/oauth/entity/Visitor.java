package com.heartape.hap.oauth.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 系统用户
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Visitor {

    public Visitor() {
    }

    public Visitor(Long uid, String username, String password, String nickname, String avatar, String role, String mobile, String email, LocalDateTime createTime) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.role = role;
        this.mobile = mobile;
        this.email = email;
        this.createTime = createTime;
    }

    /**用户id*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    /**用户账户*/
    private String username;

    /**用户密码*/
    private String password;

    /**用户昵称*/
    private String nickname;

    /**用户头像*/
    private String avatar;

    /**用户角色*/
    private String role;

    /**用户手机号*/
    private String mobile;

    /**邮箱*/
    private String email;

    /**创建时间*/
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
