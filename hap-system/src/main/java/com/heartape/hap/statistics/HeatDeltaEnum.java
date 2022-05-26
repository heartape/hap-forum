package com.heartape.hap.statistics;

/**
 * 热度变化值定义
 */
public enum HeatDeltaEnum {

    ARTICLE_INIT(100),
    ARTICLE_SELECT(10),
    ARTICLE_LIKE(20),
    ARTICLE_DISLIKE(15),
    TOPIC_INIT(100),
    TOPIC_SELECT(10),
    DISCUSS_INIT(100);

    private final int delta;

    HeatDeltaEnum(int delta) {
        this.delta = delta;
    }

    public int getDelta() {
        return delta;
    }
}
