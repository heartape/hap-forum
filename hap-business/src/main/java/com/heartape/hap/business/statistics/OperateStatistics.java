package com.heartape.hap.business.statistics;

public interface OperateStatistics {

    /** 用于拼接redis key */
    String UID = "uid";

    /**
     * 资源操作是否被记录
     */
    boolean getOperate(long sourceId, long uid);

    /**
     * 记录资源受到的用户操作,返回true表示操作成功
     */
    boolean setOperate(long sourceId, long uid);

    /**
     * 移除资源受到的用户操作,返回true表示操作成功
     */
    boolean removeOperate(long sourceId, long uid);

    /**
     * 当前用户是否已经对目标资源进行操作
     */
    boolean getPeopleOperate(long uid, long sourceId);

    /**
     * 记录用户对目标资源的操作行为,返回true表示操作成功
     */
    boolean setPeopleOperate(long uid, long sourceId);

    /**
     * 移除用户对目标资源的操作行为,返回true表示操作成功
     */
    boolean removePeopleOperate(long uid, long sourceId);
}
