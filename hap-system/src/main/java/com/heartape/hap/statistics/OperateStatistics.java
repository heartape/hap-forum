package com.heartape.hap.statistics;

/**
 * 资源操作单向统计
 */
public interface OperateStatistics {

    /**
     * 是否存在
     */
    boolean select(long resourceId, long sponsorId);

    /**
     * 操作数
     */
    long count(long resourceId);

    /**
     * 记录资源受到的用户操作,返回操作后数量
     */
    long insert(long resourceId, long sponsorId);

    /**
     * 删除统计
     */
    boolean remove(long resourceId);

}
