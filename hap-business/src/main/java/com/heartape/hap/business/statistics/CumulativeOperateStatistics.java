package com.heartape.hap.business.statistics;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * 资源累计操作统计，如热度等可重复操作
 */
public interface CumulativeOperateStatistics {

    /**
     * 资源被操作数
     */
    int operateNumber(Long mainId, long sourceId);

    /**
     * 资源被操作数
     */
    int operateNumberAll(Long mainId);

    /**
     * 资源操作数增加
     */
    int operateIncrement(Long mainId, long sourceId, int delta);

    /**
     * 资源操作数减少
     */
    int operateDecrement(Long mainId, long sourceId, int delta);

    /**
     * 修改资源操作数倍数
     */
    int operateMultiple(Long mainId, long sourceId, double delta);

    /**
     * 根据资源操作数分页查询
     */
    Set<ZSetOperations.TypedTuple<Long>> operateNumberPage(Long mainId, int pageNum, int pageSize);

    /**
     * 移除资源受到的用户操作
     */
    boolean removeOperate(Long mainId, long sourceId);

}
