package com.heartape.hap.business.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {

    @JsonIgnore
    @TableField(fill = FieldFill.INSERT, select = false)
    private Boolean status;

    @TableField(value = "created_time", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime createdTime;

    @JsonIgnore
    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
}
