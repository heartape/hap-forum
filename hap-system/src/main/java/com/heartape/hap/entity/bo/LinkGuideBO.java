package com.heartape.hap.entity.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@Data
public class LinkGuideBO {

    private Long guideId;

    private String title;

    private String path;

}
