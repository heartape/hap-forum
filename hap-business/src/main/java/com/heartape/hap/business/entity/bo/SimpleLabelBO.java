package com.heartape.hap.business.entity.bo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class SimpleLabelBO {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long labelId;

    private String name;

}
