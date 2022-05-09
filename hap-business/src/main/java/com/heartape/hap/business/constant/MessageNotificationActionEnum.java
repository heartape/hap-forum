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
    LIKE(1, "like"),
    /** 点赞 */
    DISLIKE(2, "dislike"),
    /** 关注 */
    STAR(3, "star"),
    /** 讨论 */
    DISCUSS(4, "discuss"),
    /** 评论 */
    COMMENT(5, "comment"),
    /** 回复 */
    REPLY(6, "reply"),
    /** 审核通过 */
    APPROVE(7, "approve"),
    /** 审核退回 */
    BACK(8, "back"),
    /** 删除 */
    REMOVE(9, "remove");

    private final Integer code;
    private final String name;

    MessageNotificationActionEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 将action转化为对应的中文
     */
    public static String exchangeAction(Integer actionCode) {
        if (LIKE.getCode().equals(actionCode)) {
            return LIKE.getName();
        } else if (STAR.getCode().equals(actionCode)) {
            return STAR.getName();
        } else if (DISCUSS.getCode().equals(actionCode)) {
            return DISCUSS.getName();
        } else if (COMMENT.getCode().equals(actionCode)) {
            return COMMENT.getName();
        } else if (REPLY.getCode().equals(actionCode)) {
            return REPLY.getName();
        } else {
            return null;
        }
    }
}
