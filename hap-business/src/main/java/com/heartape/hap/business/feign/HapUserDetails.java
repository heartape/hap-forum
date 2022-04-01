package com.heartape.hap.business.feign;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 保存登录用户信息
 */
@Data
public class HapUserDetails {

    /**登录时间*/
    private LocalDateTime loginTime;
    private Long uid;
    private String username;
    private String nickname;
    private String profile;
    private String avatar;
    private String role;
}
