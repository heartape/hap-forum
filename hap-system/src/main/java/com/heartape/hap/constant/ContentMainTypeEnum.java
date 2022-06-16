package com.heartape.hap.constant;

public enum ContentMainTypeEnum {

    ARTICLE("article"),
    TOPIC("topic");

    private final String typeName;

    ContentMainTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
