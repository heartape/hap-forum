package com.heartape.hap.entity.bo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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

    private Long parentId;

    private List<LabelSimpleBO> children;

    private String name;

    private String mainPicture;

    private String simpleIntroduce;

    private String introduce;

}
