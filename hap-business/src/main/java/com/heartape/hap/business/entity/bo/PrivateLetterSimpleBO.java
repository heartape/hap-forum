package com.heartape.hap.business.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrivateLetterSimpleBO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long letterId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    private String avatar;

    private String nickname;

    private String simpleContent;

    private Boolean lookUp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTime;
}
