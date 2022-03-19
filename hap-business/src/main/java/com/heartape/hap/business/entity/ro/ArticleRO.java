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
 * @since 2022-03-12
 */
@Data
public class ArticleRO {

    private String title;

    private String content;

    private Boolean isPicture;

    private String mainPicture;

    @ApiModelProperty("多个labelId组成的jsonArray")
    private List<Long> labelId;

}
