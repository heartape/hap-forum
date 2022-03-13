package com.heartape.hap.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId(value = "topic_id", type = IdType.ASSIGN_ID)
    private Long topicId;

    @TableField("title")
    private String title;

    @TableField("simple_description")
    private String simpleDescription;

    @TableField("description")
    private String description;

    @TableField("is_long")
    private Boolean isLong;

    @TableField(value = "label_id", typeHandler = JacksonTypeHandler.class)
    private List<Long> labelId;

    @ApiModelProperty("置顶条件，根据不同的搜索条件判断是否置顶（暂不实现）")
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Topping topping;

}
