package com.heartape.hap.business.constant;

/**
 * 定义记录资源操作的redis key
 */
public class ResourceRedisKeyConstant {
    // action
    private final static String LIKE = "like:";

    // resource
    private final static String ARTICLE = "a:";
    private final static String ARTICLE_COMMENT = "ac:";
    private final static String ARTICLE_COMMENT_CHILD = "acc:";
    private final static String TOPIC = "t:";
    private final static String DISCUSS = "d:";
    private final static String DISCUSS_COMMENT = "dc:";
    private final static String DISCUSS_COMMENT_CHILD = "dcc:";

    // combination
    public final static String LIKE_ARTICLE = LIKE + ARTICLE;
    public final static String LIKE_ARTICLE_COMMENT = LIKE + ARTICLE_COMMENT;
    public final static String LIKE_ARTICLE_COMMENT_CHILD = LIKE + ARTICLE_COMMENT_CHILD;
    public final static String LIKE_TOPIC = LIKE + TOPIC;
    public final static String LIKE_DISCUSS = LIKE + DISCUSS;
    public final static String LIKE_DISCUSS_COMMENT = LIKE + DISCUSS_COMMENT;
    public final static String LIKE_DISCUSS_COMMENT_CHILD = LIKE + DISCUSS_COMMENT_CHILD;
}
