package com.heartape.hap.business.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContentManage {

    private Long id;

    private Long mainId;

    private String title;

    private String simpleContent;

    private String content;

    private LocalDateTime createdTime;

}
