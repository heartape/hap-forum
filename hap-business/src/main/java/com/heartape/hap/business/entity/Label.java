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
public class Label extends BaseEntity {

    @TableId(value = "label_id", type = IdType.AUTO)
    private Long labelId;

    @ApiModelProperty("最多3级")
    private Long level;

    @TableField(value = "parent_id",typeHandler = JacksonTypeHandler.class)
    private List<Long> parentId;

    private String name;

    @TableField("main_picture")
    private String mainPicture;

    @TableField("simple_introduce")
    private String simpleIntroduce;

    private String introduce;

}
