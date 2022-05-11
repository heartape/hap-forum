package com.heartape.hap.business.entity.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LabelChildRO {

    @ApiModelProperty("一级标签没有parentId")
    private Long parentId;

    private String name;

    private String mainPicture;

    private String introduce;

}
