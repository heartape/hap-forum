package com.heartape.hap.business.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@Data
public class ArticleCommentChildBO {

    private Long commentId;

    private Long uid;

    private String avatar;

    private String nickname;

    private Boolean childToChild;

    private Long childTarget;

    private String childTargetName;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTime;
}