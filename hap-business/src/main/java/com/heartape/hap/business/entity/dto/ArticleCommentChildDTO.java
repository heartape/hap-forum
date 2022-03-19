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
public class ArticleCommentChildDTO {

    private Long articleId;

    private Long parentId;

    private Boolean childToChild;

    private Long childTarget;

    private String content;

}
