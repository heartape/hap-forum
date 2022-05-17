package com.heartape.hap.business.statistics;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * sourceId:发起方id，即进行操作的一方，如点赞时的uid，收藏时的文件夹id
 */
public abstract class AbstractPairOperateStatistics implements PairOperateStatistics {

    public abstract ZSetOperations<String, Long> getZSetOperations();

    public abstract String getKeyHeader(long sourceId);

    public abstract String getSponsorKeyHeader(long sponsorId);

    /**
     * 资源列表和时间戳
     */
    public Set<TypedTuple<Long>> getSourceList(long sponsorId, int pageNum, int pageSize) {
        String userKey = getSponsorKeyHeader(sponsorId);
        long start = (long) (pageNum - 1) * pageSize;
        long end = (long) pageNum * pageSize -1;
        return getZSetOperations().rangeWithScores(userKey, start, end);
    }

    /**
     * 资源操作时间戳,0表示不存在
     */
    public long getSourceOperate(long sourceId, long sponsorId){
        String sourceKey = getKeyHeader(sourceId);
        Double score = getZSetOperations().score(sourceKey, sponsorId);
        return score != null ? score.longValue() : 0;
    }

    /**
     * 当前用户对目标资源进行操作时间戳,0表示不存在
     */
    public long getPeopleOperate(long sourceId, long sponsorId){
        String sponsorKey = getSponsorKeyHeader(sponsorId);
        Double score = getZSetOperations().score(sponsorKey, sourceId);
        return score != null ? score.longValue() : 0;
    }

    /**
     * 记录操作
     * @return 返回最终操作，true:添加，false：取消
     */
    public boolean setOperate(long sourceId, long sponsorId, long timestamp){
        String sourceKey = getKeyHeader(sourceId);
        String sponsorKey = getSponsorKeyHeader(sponsorId);
        String script = LuaScript.OPERATE_SET;
        List<String> keys = new ArrayList<>();
        keys.add(sourceKey);
        keys.add(sponsorKey);
        Boolean result = getZSetOperations().getOperations().execute(RedisScript.of(script, Boolean.class), keys, sponsorId, sourceId, timestamp);
        return Objects.equals(result, true);
    }

    /**
     * 仅删除source的set，uid的set脏数据待用户查询列表时提示已删除
     */
    @Override
    public boolean removeSourceOperate(long sourceId) {
        String sourceKey = getKeyHeader(sourceId);
        Boolean delete = getZSetOperations().getOperations().delete(sourceKey);
        return Objects.equals(delete, true);
    }

    /**
     * 仅删除uid的set，source的set脏数据待资源作者查询列表时提示账号已禁用
     */
    @Override
    public boolean removePeopleOperate(long sponsorId) {
        String sponsorKey = getSponsorKeyHeader(sponsorId);
        Boolean delete = getZSetOperations().getOperations().delete(sponsorKey);
        return Objects.equals(delete, true);
    }

}
