package com.heartape.hap.business.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
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
public class Topic extends BaseEntity {

    @TableId(value = "topic_id", type = IdType.AUTO)
    private Long topicId;

    @TableField(fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private Long uid;

    @TableField(fill = FieldFill.INSERT)
    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    private String nickname;

    @TableField(fill = FieldFill.INSERT)
    private String profile;

    private String title;

    @TableField(value = "is_picture", updateStrategy = FieldStrategy.NEVER)
    private Boolean isPicture;

    @TableField(value = "main_picture", updateStrategy = FieldStrategy.NEVER)
    private String mainPicture;

    @TableField(value = "simple_description", updateStrategy = FieldStrategy.NEVER)
    private String simpleDescription;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String description;

    @TableField(value = "label_id", typeHandler = JacksonTypeHandler.class)
    private List<Long> labelId;

    @TableField(exist = false)
    private List<Label> label;

    @ApiModelProperty("置顶条件，根据不同的搜索条件判断是否置顶（暂不实现）")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Topping topping;

}
