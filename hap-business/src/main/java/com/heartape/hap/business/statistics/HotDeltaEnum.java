package com.heartape.hap.business.statistics;

/**
 * 热度变化值定义
 */
public enum HotDeltaEnum {

    ARTICLE_SELECT(10),
    ARTICLE_LIKE(20),
    ARTICLE_DISLIKE(15),
    TOPIC_SELECT(10);

    private final int delta;

    HotDeltaEnum(int delta) {
        this.delta = delta;
    }

    public int getDelta() {
        return delta;
    }
}
