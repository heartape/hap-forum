package com.heartape.hap.business.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrivateLetterBO{

    @JsonSerialize(using = ToStringSerializer.class)
    private Long letterId;

    private String content;

    private Boolean lookUp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTime;
}
