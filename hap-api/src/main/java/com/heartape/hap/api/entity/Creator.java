package com.heartape.hap.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class Creator {

    @TableId(value = "uid", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    private String email;

    private String mobile;

    private String password;

    private String nickname;

    private String avatar;

    private String role;

    /**账户状态*/
    private String accountStatus;

    public Creator(Long uid, String email, String mobile, String password, String nickname, String avatar, String role) {
        this.uid = uid;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.nickname = nickname;
        this.avatar = avatar;
        this.role = role;
        this.accountStatus = "1";
    }
}
