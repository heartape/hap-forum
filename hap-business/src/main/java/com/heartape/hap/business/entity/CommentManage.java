package com.heartape.hap.business.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentManage {

    private Long commentId;

    private Long mainId;

    /**
     * 讨论没有title，为避免3表联查，查出topic_id
     */
    private Long titleId;

    private String title;

    private Long uid;

    private String avatar;

    private String nickname;

    private String comment;

    private LocalDateTime createdTime;
}
