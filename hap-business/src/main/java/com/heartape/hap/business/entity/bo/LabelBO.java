package com.heartape.hap.business.entity.bo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class LabelBO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long labelId;

    private Integer level;

    private List<Long> parentId;

    private List<Long> childId;

    private String name;

    private String mainPicture;

    private String simpleIntroduce;

    private String introduce;

}
