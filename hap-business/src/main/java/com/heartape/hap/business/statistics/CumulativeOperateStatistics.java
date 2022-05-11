package com.heartape.hap.business.statistics;

/**
 * 数据累计操作统计
 */
public interface CumulativeOperateStatistics {

    /**
     * 资源被操作数
     */
    int operateNumber(long sourceId);

    /**
     * 记录资源操作
     */
    int operateIncrement(long sourceId, int delta);

    /**
     * 修改资源操作数倍数
     */
    int operateMultipleIncrement(long sourceId, double delta);

    /**
     * 记录资源操作
     */
    int operateDecrement(long sourceId, int delta);

    /**
     * 移除资源受到的用户操作
     */
    boolean removeOperate(long sourceId);

}
