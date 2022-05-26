package com.heartape.hap.statistics;

import lombok.Data;

import java.util.List;

/**
 * 主从统计，用于实现收藏夹
 */
public interface MasterSlaveOperateStatistics {

    @Data
    class Slave {
        private Long slaveId;
        private Long timestamp;
    }

    /**
     * slave数据查询
     */
    long total(long slaveId);

    /**
     * 检查是否超出范围
     */
    boolean check(int total, int pageNum, int pageSize);

    /**
     * master分页查询
     */
    List<Slave> select(long masterId, int pageNum, int pageSize);

    /**
     * slave数据查询
     */
    long slaveCount(long slaveId);

    /**
     * 记录master和slave
     */
    long insert(long masterId, long slaveId);

    /**
     * 删除master记录，slave-1
     */
    boolean delete(long masterId, long slaveId);

    /**
     * 移除slave，master不做操作，查询到已删除slave时标识为已删除
     */
    boolean remove(long slaveId);

}
