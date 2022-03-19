package com.heartape.hap.business.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author heartape
 * @since 2022-03-12
 */
@Data
public class ArticleDetailBO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long articleId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    private String nickname;

    private String profile;

    private String avatar;

    private Boolean isPicture;

    private String mainPicture;

    private String title;

    private String content;

    private List<LabelBO> label;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTime;

    private PageInfo<ArticleCommentBO> comment;

}