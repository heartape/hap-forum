package com.heartape.hap.business.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PrivateLetterCreatorBO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    private String avatar;

    private String nickname;

    /** 该用户的未读私信数量 */
    private Integer unread;

    /** 最后一次私信的时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTime;
}
