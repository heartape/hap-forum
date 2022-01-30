package com.heartape.hap.admin.entity.security;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class VisitorInfo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    private String username;

    private String nickname;

    private String avatar;

    private String role;
}
