package com.heartape.hap.entity.ro;

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
public class DiscussCommentRO {

    private Long topicId;

    private Long discussId;

    private String content;
}
