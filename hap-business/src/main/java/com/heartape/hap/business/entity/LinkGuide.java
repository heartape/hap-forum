package com.heartape.hap.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class LinkGuide extends BaseEntity {

    @TableId(value = "guide_id", type = IdType.AUTO)
    private Long guideId;

    private String title;

    @ApiModelProperty("外部链接")
    private String path;

    @ApiModelProperty("是否置顶")
    private Boolean topping;

    @ApiModelProperty("排序")
    private Integer sequence;

}
