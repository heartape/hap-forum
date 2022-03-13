package com.heartape.hap.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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

    @TableId(value = "guide_id", type = IdType.ASSIGN_ID)
    private Long guideId;

    @TableField("title")
    private String title;

    @ApiModelProperty("外部链接")
    @TableField("path")
    private String path;

    @ApiModelProperty("是否置顶")
    @TableField("top")
    private Boolean top;

    @ApiModelProperty("排序")
    @TableField("order")
    private Integer order;

    @TableField("publish_time")
    private LocalDateTime publishTime;


}
