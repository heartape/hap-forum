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
public class DiscussCommentChildRO {

    private Long topicId;

    private Long discussId;

    private Long parentId;

    private Boolean childToChild;

    private Long childTarget;

    private String content;

}
