package com.heartape.hap.business.constant;

/**
 * 目标类型
 */
public enum MessageNotificationTargetTypeEnum {

    /** 文章 */
    ARTICLE("11", "article"),
    /** 文章评论 */
    ARTICLE_COMMENT("12", "article_comment"),
    /** 文章子评论 */
    ARTICLE_COMMENT_CHILD("13", "article_comment_child"),
    /** 话题 */
    TOPIC("21", "topic"),
    /** 讨论 */
    DISCUSS("22", "discuss"),
    /** 讨论评论 */
    DISCUSS_COMMENT("23", "discuss_comment"),
    /** 讨论子评论 */
    DISCUSS_COMMENT_CHILD("24", "discuss_comment_child"),
    /** 标签 */
    LABEL("31", "label"),
    /** 用户 */
    CREATOR("41", "creator");

    private final String typeCode;
    private final String typeName;

    MessageNotificationTargetTypeEnum(String typeCode, String typeName) {
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
