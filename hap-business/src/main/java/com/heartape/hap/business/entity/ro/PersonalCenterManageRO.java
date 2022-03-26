package com.heartape.hap.business.entity.ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class PersonalCenterManageRO {
    private Integer pageNum;
    private Integer pageSize;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;
}
