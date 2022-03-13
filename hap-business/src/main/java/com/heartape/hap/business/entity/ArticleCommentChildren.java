package com.heartape.hap.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleCommentChildren extends BaseEntity {

    @TableId(value = "comment_id", type = IdType.ASSIGN_ID)
    private Long commentId;

    @TableField("article_id")
    private Long articleId;

    @TableField("parent_id")
    private Long parentId;

    @TableField("uid")
    private Long uid;

    @TableField("content")
    private String content;

    @TableField("publish_time")
    private LocalDateTime publishTime;


}
