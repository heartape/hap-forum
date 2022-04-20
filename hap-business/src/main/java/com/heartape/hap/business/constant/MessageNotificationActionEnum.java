package com.heartape.hap.business.constant;

/**
 * 动作所对应的主题类型 com.heartape.hap.business.constant.MessageNotificationMainTypeEnum
 * LIKE：所有
 * STAR：标签，话题，作者
 * DISCUSS：话题
 * COMMENT：文章，讨论
 * REPLY：评论
 */
public enum MessageNotificationActionEnum {

    /** 点赞 */
    LIKE("1", "like"),
    /** 关注 */
    STAR("2", "star"),
    /** 讨论 */
    DISCUSS("3", "discuss"),
    /** 评论 */
    COMMENT("4", "comment"),
    /** 回复 */
    REPLY("5", "reply");

    private final String code;
    private final String name;

    MessageNotificationActionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
