package com.heartape.hap.business.entity.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@Data
public class LabelRO{

    @ApiModelProperty("最多3级")
    private Integer level;

    @ApiModelProperty("一级标签没有parentId，其他必须")
    private List<Long> parentId;

    private String name;

    private String mainPicture;

    private String introduce;

}
