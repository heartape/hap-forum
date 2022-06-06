package com.heartape.hap.constant;

/**
 * 热度变化值定义
 */
public enum HeatDeltaEnum {

    ARTICLE_INIT(100),
    ARTICLE_SELECT(10),
    ARTICLE_LIKE(20),
    ARTICLE_DISLIKE(15),
    ARTICLE_COMMENT_INIT(100),
    ARTICLE_COMMENT_LIKE(20),
    ARTICLE_COMMENT_DISLIKE(15),
    ARTICLE_COMMENT_CHILD_INIT(100),
    ARTICLE_COMMENT_CHILD_LIKE(20),
    ARTICLE_COMMENT_CHILD_DISLIKE(15),
    TOPIC_INIT(100),
    TOPIC_SELECT(10),
    DISCUSS_INIT(100),
    DISCUSS_LIKE(10),
    DISCUSS_DISLIKE(10),
    DISCUSS_COMMENT_INIT(100),
    DISCUSS_COMMENT_LIKE(10),
    DISCUSS_COMMENT_DISLIKE(10),
    DISCUSS_COMMENT_CHILD_INIT(100),
    DISCUSS_COMMENT_CHILD_LIKE(10),
    DISCUSS_COMMENT_CHILD_DISLIKE(10);

    private final int delta;

    HeatDeltaEnum(int delta) {
        this.delta = delta;
    }

    public int getDelta() {
        return delta;
    }
}
