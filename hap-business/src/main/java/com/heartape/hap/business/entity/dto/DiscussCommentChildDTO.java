package com.heartape.hap.business.entity.dto;

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
public class DiscussCommentChildDTO {

    private Long topicId;

    private Long discussId;

    private Long parentId;

    private Long childTarget;

    private String content;

}
