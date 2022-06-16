package com.heartape.hap.entity.dto;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@Data
public class DiscussCommentDTO {

    private Long topicId;

    private Long discussId;

    private String content;
}
