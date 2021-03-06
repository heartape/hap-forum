package com.heartape.hap.entity.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonalCenterManageDTO {
    private Integer pageNum;
    private Integer pageSize;
    private LocalDate startTime;
    private LocalDate endTime;
}
