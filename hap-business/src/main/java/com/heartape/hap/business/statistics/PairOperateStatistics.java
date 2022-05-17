package com.heartape.hap.business.statistics;

/**
 * 资源操作双向统计，如关注等单次单状态不可重复操作,通过timestamp排序
 */
public interface PairOperateStatistics {

    /**
     * 资源操作时间戳
     */
    long getSourceOperate(long sourceId, long sponsorId);

    /**
     * 当前用户对目标资源进行操作时间戳
     */
    long getPeopleOperate(long sponsorId, long sourceId);

    /**
     * 记录资源受到的用户操作,返回true表示操作成功
     */
    boolean setOperate(long sourceId, long sponsorId, long timestamp);

    /**
     * 删除source的set，uid的set脏数据不处理
     */
    boolean removeSourceOperate(long sourceId);

    /**
     * 仅删除uid的set，source的set脏数据不处理
     */
    boolean removePeopleOperate(long sponsorId);

}
