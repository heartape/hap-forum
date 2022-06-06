package com.heartape.hap.entity.dto;

import lombok.Data;

@Data
public class CreatorEmailDTO {

    private String email;

    private String code;
    // todo:接入邮箱验证码后，去除密码
    private String password;

    private String nickname;
}
