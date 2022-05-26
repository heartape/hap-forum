package com.heartape.hap.entity.dto;

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
public class TopicDTO {

    private String title;

    private Boolean isPicture;

    private String mainPicture;

    private String description;

    private List<Long> labelId;

}
