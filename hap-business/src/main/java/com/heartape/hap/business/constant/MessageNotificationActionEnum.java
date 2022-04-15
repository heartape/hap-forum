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

    LIKE("1"),
    STAR("2"),
    DISCUSS("3"),
    COMMENT("3"),
    REPLY("5");

    private final String action;

    MessageNotificationActionEnum(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
