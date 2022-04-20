package com.heartape.hap.business.constant;

/**
 * 主体类型
 */
public enum MessageNotificationMainTypeEnum {

    /** 文章 */
    ARTICLE("1", "article"),
    /** 话题 */
    TOPIC("2", "topic"),
    /** 标签 */
    LABEL("3", "label"),
    /** 用户 */
    CREATOR("4", "creator");

    private final String typeCode;
    private final String typeName;

    MessageNotificationMainTypeEnum(String typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getTypeName() {
        return typeName;
    }
}
