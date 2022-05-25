package com.heartape.hap.business.statistics;

import lombok.Data;

import java.util.List;

/**
 * 资源操作双向统计，如关注等单次单状态不可重复操作,通过timestamp排序
 */
public interface PairOperateStatistics {

    @Data
    class Target {
        private Long targetId;
        private Long timestamp;
    }

    /**
     * 分页查询
     */
    List<Target> page(long sponsorId, int pageNum, int pageSize);

    /**
     * 资源操作
     */
    long selectTarget(long targetId);

    /**
     * 当前用户对目标资源进行操作时间戳
     */
    long selectSponsor(long sponsorId);

    /**
     * 记录资源受到的用户操作,返回true表示操作成功
     */
    int insert(long sourceId, long sponsorId);

    /**
     * 删除source的set，uid的set脏数据不处理
     */
    boolean delete(long sourceId);

}
