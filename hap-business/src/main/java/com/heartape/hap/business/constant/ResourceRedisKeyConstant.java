package com.heartape.hap.business.constant;

/**
 * 定义记录资源操作的redis key
 */
public class ResourceRedisKeyConstant {

    private final static String AND = ":";

    // action
    private final static String LIKE = "like:";
    private final static String HOT = "hot:";

    // resource
    private final static String ARTICLE = "a";
    private final static String ARTICLE_COMMENT = "ac";
    private final static String ARTICLE_COMMENT_CHILD = "acc";
    private final static String TOPIC = "t";
    private final static String DISCUSS = "d";
    private final static String DISCUSS_COMMENT = "dc";
    private final static String DISCUSS_COMMENT_CHILD = "dcc";

    // like
    public final static String LIKE_ARTICLE = LIKE + ARTICLE + AND;
    public final static String LIKE_ARTICLE_COMMENT = LIKE + ARTICLE_COMMENT + AND;
    public final static String LIKE_ARTICLE_COMMENT_CHILD = LIKE + ARTICLE_COMMENT_CHILD + AND;
    public final static String LIKE_DISCUSS = LIKE + DISCUSS + AND;
    public final static String LIKE_DISCUSS_COMMENT = LIKE + DISCUSS_COMMENT + AND;
    public final static String LIKE_DISCUSS_COMMENT_CHILD = LIKE + DISCUSS_COMMENT_CHILD + AND;

    // hot
    public final static String HOT_ARTICLE = HOT + ARTICLE;
    public final static String HOT_ARTICLE_COMMENT = HOT + ARTICLE_COMMENT;
    public final static String HOT_ARTICLE_COMMENT_CHILD = HOT + ARTICLE_COMMENT_CHILD;
    public final static String HOT_TOPIC = HOT + TOPIC;
    public final static String HOT_DISCUSS = HOT + DISCUSS;
    public final static String HOT_DISCUSS_COMMENT = HOT + DISCUSS_COMMENT;
    public final static String HOT_DISCUSS_COMMENT_CHILD = HOT + DISCUSS_COMMENT_CHILD;
}
