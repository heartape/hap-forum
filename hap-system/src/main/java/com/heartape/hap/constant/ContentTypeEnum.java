package com.heartape.hap.constant;

public enum ContentTypeEnum {

    ARTICLE("article"),
    TOPIC("topic"),
    DISCUSS("discuss");

    private final String typeName;

    ContentTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
