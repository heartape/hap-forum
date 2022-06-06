package com.heartape.hap.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author heartape
 * @since 2022-03-12
 */
@Data
public class ArticleSimpleBO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;

    private Boolean isPicture;

    private String mainPicture;

    private String title;

    private String simpleContent;

    /**
     * 两个注解组合使用
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdTime;

    private Integer like;

    private Integer dislike;

}