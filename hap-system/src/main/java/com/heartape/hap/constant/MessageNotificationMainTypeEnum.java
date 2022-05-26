package com.heartape.hap.constant;

/**
 * 主体类型
 */
public enum MessageNotificationMainTypeEnum {

    /** 文章 */
    ARTICLE(1, "article"),
    /** 话题 */
    TOPIC(2, "topic"),
    /** 标签 */
    LABEL(3, "label"),
    /** 用户 */
    CREATOR(4, "creator");

    private final Integer typeCode;
    private final String typeName;

    MessageNotificationMainTypeEnum(Integer typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * 将mainType转化为对应的中文
     */
    public static String exchangeMainType(Integer mainTypeCode) {
        if (ARTICLE.getTypeCode().equals(mainTypeCode)) {
            return ARTICLE.getTypeName();
        } else if (TOPIC.getTypeCode().equals(mainTypeCode)) {
            return TOPIC.getTypeName();
        } else if (LABEL.getTypeCode().equals(mainTypeCode)) {
            return LABEL.getTypeName();
        } else if (CREATOR.getTypeCode().equals(mainTypeCode)) {
            return CREATOR.getTypeName();
        } else {
            return null;
        }
    }
}
