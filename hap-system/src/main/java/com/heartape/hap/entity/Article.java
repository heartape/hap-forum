package com.heartape.hap.entity;

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

    /**
     * TableId 注解会自动在insert时返回该主键
     * type = IdType.AUTO时会使用sharding的分布式主键
     */
    @TableId(value = "article_id", type = IdType.AUTO)
    private Long articleId;

    private Long uid;

    private String avatar;

    private String nickname;

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
