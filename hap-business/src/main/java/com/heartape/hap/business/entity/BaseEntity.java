package com.heartape.hap.business.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
    private Integer createdBy;
    private LocalDateTime createdTime;
    private Integer updatedBy;
    private LocalDateTime updatedTime;
}
