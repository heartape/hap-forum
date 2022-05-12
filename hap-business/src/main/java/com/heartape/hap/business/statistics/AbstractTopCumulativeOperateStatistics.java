package com.heartape.hap.business.statistics;

import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

public abstract class AbstractTopCumulativeOperateStatistics extends AbstractCumulativeOperateStatistics {

    /**
     * 顶级资源没有上一级主体
     * @param mainId 将会被舍弃
     */
    @Override
    public String getKey(Long mainId) {
        return getKey();
    }

    public abstract String getKey();

    public int operateNumber(long sourceId) {
        return super.operateNumber(null, sourceId);
    }

    public int operateNumberAll() {
        return super.operateNumberAll(null);
    }

    public int operateIncrement(long sourceId, int delta) {
        return super.operateIncrement(null, sourceId, delta);
    }

    public int operateDecrement(long sourceId, int delta) {
        return super.operateDecrement(null, sourceId, delta);
    }

    public int operateMultiple(long sourceId, double multiple) {
        return super.operateMultiple(null, sourceId, multiple);
    }

    public Set<ZSetOperations.TypedTuple<Long>> operateNumberPage(int pageNum, int pageSize) {
        return super.operateNumberPage(null, pageNum, pageSize);
    }

    public boolean removeOperate(long sourceId) {
        return super.removeOperate(null, sourceId);
    }
}
