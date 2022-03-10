package com.heartape.hap.api.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class CreatorInfo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    private String username;

    private String nickname;

    private String avatar;

    private String role;

    public CreatorInfo() {
    }

    public CreatorInfo(Long uid, String username, String nickname, String avatar, String role) {
        this.uid = uid;
        this.username = username;
        this.nickname = nickname;
        this.avatar = avatar;
        this.role = role;
    }
}
