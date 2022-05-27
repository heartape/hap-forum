package com.heartape.hap;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;

import java.time.LocalDateTime;

public class BaseEntity {

    @TableField(fill = FieldFill.INSERT, select = false)
    private Boolean status;

    @TableField(value = "created_time", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime createdTime;

    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;
}
