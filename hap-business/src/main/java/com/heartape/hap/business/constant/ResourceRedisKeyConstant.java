package com.heartape.hap.business.constant;

/**
 * 定义记录资源操作的redis key
 */
public class ResourceRedisKeyConstant {

    /** sponsorId:发起方id，即进行操作的一方，如点赞时的uid，收藏时的文件夹id */
    public final static String SPONSOR = "s:";

    // resource
    private final static String ARTICLE = "a";
    private final static String ARTICLE_COMMENT = "ac";
    private final static String ARTICLE_COMMENT_CHILD = "acc";
    private final static String TOPIC = "t";
    private final static String DISCUSS = "d";
    private final static String DISCUSS_COMMENT = "dc";
    private final static String DISCUSS_COMMENT_CHILD = "dcc";
    private final static String LABEL = "l";

    // like
    public final static String LIKE_ARTICLE = "like:a:";
    public final static String LIKE_ARTICLE_COMMENT = "like:ac:";
    public final static String LIKE_ARTICLE_COMMENT_CHILD = "like:acc:";
    public final static String LIKE_DISCUSS = "like:d:";
    public final static String LIKE_DISCUSS_COMMENT = "like:dc:";
    public final static String LIKE_DISCUSS_COMMENT_CHILD = "like:dcc:";

    // hot
    public final static String HOT_ARTICLE = "hot:a";
    public final static String HOT_ARTICLE_COMMENT = "hot:ac:";
    public final static String HOT_ARTICLE_COMMENT_CHILD = "hot:acc:";
    public final static String HOT_TOPIC = "hot:t";
    public final static String HOT_DISCUSS = "hot:d:";
    public final static String HOT_DISCUSS_COMMENT = "hot:dc:";
    public final static String HOT_DISCUSS_COMMENT_CHILD = "hot:dcc:";

    // follow
    public final static String FOLLOW_TOPIC = "like:t:";
    public final static String FOLLOW_LABEL = "like:l:";
    public final static String FOLLOW_CREATOR = "like:c:";

    // collection
    public final static String COLLECTION_ARTICLE = "collect:a:";
    public final static String COLLECTION_DISCUSS = "collect:d:";
}
