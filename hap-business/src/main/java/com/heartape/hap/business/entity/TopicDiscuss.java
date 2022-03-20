package com.heartape.hap.business.entity;

import com.baomidou.mybatisplus.annotation.*;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
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
public class TopicDiscuss extends BaseEntity {

    @TableId(value = "discuss_id", type = IdType.AUTO)
    private Long discussId;

    @TableField("topic_id")
    private Long topicId;

    @TableField(fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private Long uid;

    @TableField(fill = FieldFill.INSERT)
    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    private String nickname;

    @TableField(fill = FieldFill.INSERT)
    private String profile;

    private String content;

    @ApiModelProperty("置顶条件，根据不同的搜索条件判断是否置顶（暂不实现）")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Topping topping;

}
