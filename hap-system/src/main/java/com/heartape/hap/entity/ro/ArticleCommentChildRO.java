package com.heartape.hap.entity.ro;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class ArticleCommentChildRO {

    private Long articleId;

    private Long parentId;

    private Boolean childToChild;

    private Long childTarget;

    private String content;

}
