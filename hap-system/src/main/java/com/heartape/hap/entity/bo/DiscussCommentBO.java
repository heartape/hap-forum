package com.heartape.hap.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@Data
public class DiscussCommentBO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long uid;

    private String avatar;

    private String nickname;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTime;

    private Integer like;

    private Integer dislike;

    /**评论总数*/
    private Integer total;

    private List<DiscussCommentChildBO> simpleChildren;
}
