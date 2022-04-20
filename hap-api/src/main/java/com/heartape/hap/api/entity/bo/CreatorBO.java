package com.heartape.hap.api.entity.bo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class CreatorBO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    private String username;

    private String nickname;

    private String avatar;

    private String role;
}
