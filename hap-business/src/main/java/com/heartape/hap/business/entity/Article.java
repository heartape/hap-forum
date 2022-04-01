package com.heartape.hap.business.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author heartape
 * @since 2022-03-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Article extends BaseEntity {

    @TableId(value = "article_id", type = IdType.AUTO)
    private Long articleId;

    @TableField(fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private Long uid;

    @TableField(fill = FieldFill.INSERT)
    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    private String nickname;

    @TableField(fill = FieldFill.INSERT)
    private String profile;

    @TableField(value = "is_picture", updateStrategy = FieldStrategy.NEVER)
    private Boolean isPicture;

    @TableField(value = "main_picture", updateStrategy = FieldStrategy.NEVER)
    private String mainPicture;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String title;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String content;

    @TableField(value = "simple_content", updateStrategy = FieldStrategy.NEVER)
    private String simpleContent;

    @TableField(value = "label_id",typeHandler = JacksonTypeHandler.class, updateStrategy = FieldStrategy.NEVER)
    private List<Long> labelId;

    @TableField(exist = false)
    private List<Label> label;

    @Deprecated
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Topping topping;

}
