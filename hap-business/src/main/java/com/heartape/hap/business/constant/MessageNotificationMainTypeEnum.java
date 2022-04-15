package com.heartape.hap.business.constant;

public enum MessageNotificationMainTypeEnum {

    ARTICLE("11"),
    ARTICLE_COMMENT("12"),
    ARTICLE_COMMENT_CHILD("13"),
    TOPIC("21"),
    DISCUSS("22"),
    DISCUSS_COMMENT("23"),
    DISCUSS_COMMENT_CHILD("24"),
    LABEL("3"),
    CREATOR("4");

    private final String typeName;

    MessageNotificationMainTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }
}
