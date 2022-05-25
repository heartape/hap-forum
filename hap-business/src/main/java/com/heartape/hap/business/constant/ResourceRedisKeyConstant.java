package com.heartape.hap.business.constant;

/**
 * 定义记录资源操作的redis key
 */
public class ResourceRedisKeyConstant {

    /** sponsorId:发起方id，即进行操作的一方，如点赞时的uid，收藏时的文件夹id */
    public final static String POSITIVE = "po:";
    public final static String NEGATIVE = "ne:";
    public final static String SPONSOR = "sp:";
    public final static String SLAVE = "sl:";

    // resource
    private final static String ARTICLE = "ar";
    private final static String ARTICLE_COMMENT = "ac";
    private final static String ARTICLE_COMMENT_CHILD = "acc";
    private final static String TOPIC = "to";
    private final static String DISCUSS = "di";
    private final static String DISCUSS_COMMENT = "dc";
    private final static String DISCUSS_COMMENT_CHILD = "dcc";
    private final static String LABEL = "la";

    // like
    public final static String LIKE_ARTICLE = "like:ar:";
    public final static String LIKE_ARTICLE_COMMENT = "like:ac:";
    public final static String LIKE_ARTICLE_COMMENT_CHILD = "like:acc:";
    public final static String LIKE_DISCUSS = "like:di:";
    public final static String LIKE_DISCUSS_COMMENT = "like:dc:";
    public final static String LIKE_DISCUSS_COMMENT_CHILD = "like:dcc:";

    // hot
    public final static String HEAT_ARTICLE = "heat:ar";
    public final static String HEAT_ARTICLE_COMMENT = "heat:ac:";
    public final static String HEAT_ARTICLE_COMMENT_CHILD = "heat:acc:";
    public final static String HEAT_TOPIC = "heat:to";
    public final static String HEAT_DISCUSS = "heat:di:";
    public final static String HEAT_DISCUSS_COMMENT = "heat:dc:";
    public final static String HEAT_DISCUSS_COMMENT_CHILD = "heat:dcc:";

    // follow
    public final static String FOLLOW_TOPIC = "follow:to:";
    public final static String FOLLOW_CREATOR = "follow:cr:";

    // browse浏览记录
    public final static String BROWSE_LABEL = "browse:la:";

    // collection
    public final static String COLLECTION_ARTICLE = "collect:ar:";
    public final static String COLLECTION_DISCUSS = "collect:di:";
}
