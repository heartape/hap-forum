package com.heartape.hap.statistics;

import lombok.Data;

import java.util.List;

/**
 * 资源累计操作统计，用于顶级资源的热度
 * todo:由于顶级资源太多，会成为热key和大key，需要进行拆分
 */
public interface TopCumulativeOperateStatistics {

    /**
     * 资源被操作数
     */
    int count(long targetId);

    /**
     * 资源操作数增加
     */
    int updateIncrement(long targetId, int delta);

    /**
     * 资源操作数减少
     */
    int updateDecrement(long targetId, int delta);

    /**
     * 修改资源操作数倍数
     */
    int updateMultiple(long targetId, double delta);

    /**
     * 根据资源操作数分页查询
     */
    List<CumulativeValue> selectPage(int pageNum, int pageSize);

    /**
     * 移除资源受到的用户操作
     */
    boolean delete(long targetId);

    @Data
    class CumulativeValue {
        private Long resourceId;
        private Integer operate;
    }

}
