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
public class ArticleCommentChildDTO {

    private Long articleId;

    private Long parentId;

    private Long childTarget;

    private String content;

}
