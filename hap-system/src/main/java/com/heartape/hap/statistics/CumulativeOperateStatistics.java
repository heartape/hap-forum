package com.heartape.hap.statistics;

import lombok.Data;

import java.util.List;

/**
 * 资源累计操作统计，如热度
 */
public interface CumulativeOperateStatistics {

    /**
     * 操作数
     */
    int count(long mainId, long resourceId);

    /**
     * 操作数增加
     */
    int updateIncrement(long mainId, long resourceId, int delta);

    /**
     * 修改操作数倍数
     */
    int updateMultiple(long mainId, long resourceId, double delta);

    /**
     * 根据操作数分页查询
     */
    List<CumulativeValue> selectPage(long mainId, int pageNum, int pageSize);

    boolean delete(long mainId);

    boolean delete(long mainId, long resourceId);

    @Data
    class CumulativeValue {
        private Long resourceId;
        private Integer operate;
    }

}
