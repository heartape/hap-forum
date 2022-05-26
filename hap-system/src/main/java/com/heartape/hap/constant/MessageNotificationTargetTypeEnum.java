package com.heartape.hap.constant;

/**
 * 目标类型
 */
public enum MessageNotificationTargetTypeEnum {

    /** 文章 */
    ARTICLE(11, "article"),
    /** 文章评论 */
    ARTICLE_COMMENT(12, "article_comment"),
    /** 文章子评论 */
    ARTICLE_COMMENT_CHILD(13, "article_comment_child"),
    /** 话题 */
    TOPIC(21, "topic"),
    /** 讨论 */
    DISCUSS(22, "discuss"),
    /** 讨论评论 */
    DISCUSS_COMMENT(23, "discuss_comment"),
    /** 讨论子评论 */
    DISCUSS_COMMENT_CHILD(24, "discuss_comment_child"),
    /** 标签 */
    LABEL(31, "label"),
    /** 用户 */
    CREATOR(41, "creator");

    private final Integer typeCode;
    private final String typeName;

    MessageNotificationTargetTypeEnum(Integer typeCode, String typeName) {
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
     * 将targetType转化为对应的中文
     */
    public static String exchangeTargetType(Integer targetTypeCode) {
        if (ARTICLE.getTypeCode().equals(targetTypeCode)) {
            return ARTICLE.getTypeName();
        } else if (ARTICLE_COMMENT.getTypeCode().equals(targetTypeCode)) {
            return ARTICLE_COMMENT.getTypeName();
        } else if (ARTICLE_COMMENT_CHILD.getTypeCode().equals(targetTypeCode)) {
            return ARTICLE_COMMENT_CHILD.getTypeName();
        } else if (TOPIC.getTypeCode().equals(targetTypeCode)) {
            return TOPIC.getTypeName();
        } else if (DISCUSS.getTypeCode().equals(targetTypeCode)) {
            return DISCUSS.getTypeName();
        } else if (DISCUSS_COMMENT.getTypeCode().equals(targetTypeCode)) {
            return DISCUSS_COMMENT.getTypeName();
        } else if (DISCUSS_COMMENT_CHILD.getTypeCode().equals(targetTypeCode)) {
            return DISCUSS_COMMENT_CHILD.getTypeName();
        } else if (LABEL.getTypeCode().equals(targetTypeCode)) {
            return LABEL.getTypeName();
        } else if (CREATOR.getTypeCode().equals(targetTypeCode)) {
            return CREATOR.getTypeName();
        } else {
            return null;
        }
    }
}
