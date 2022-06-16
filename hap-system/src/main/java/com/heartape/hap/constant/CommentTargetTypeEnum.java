package com.heartape.hap.constant;

public enum CommentTargetTypeEnum {

    ARTICLE("article"),
    DISCUSS("discuss");

    private final String typeName;

    CommentTargetTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
