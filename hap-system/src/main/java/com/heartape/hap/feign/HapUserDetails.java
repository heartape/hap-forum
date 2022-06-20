package com.heartape.hap.feign;

import lombok.Data;

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
