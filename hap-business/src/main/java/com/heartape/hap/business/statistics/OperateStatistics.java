package com.heartape.hap.business.statistics;

/**
 * 资源操作单向统计，即不作sponsor和source双向的统计
 */
public interface OperateStatistics {

    /**
     * 资源操作时间戳,0表示不存在
     */
    long getOperate(long sponsorId, long sourceId);

    /**
     * 记录资源受到的用户操作,返回true表示操作成功
     */
    boolean setOperate(long sponsorId, long sourceId, long timestamp);

    /**
     * 删除统计
     */
    boolean removeOperate(long sponsorId);

}
