package com.heartape.hap.business.entity.ro;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class ArticleCommentRO {

    private Long articleId;

    private String content;

}