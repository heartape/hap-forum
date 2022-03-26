package com.heartape.hap.business.constant;

public enum CommentMainTypeEnum {

    ARTICLE("article"),
    TOPIC("topic");

    private final String typeName;

    CommentMainTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
