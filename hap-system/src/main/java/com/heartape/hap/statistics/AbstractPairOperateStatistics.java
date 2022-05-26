package com.heartape.hap.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * sponsorId:发起方id，即进行操作的一方，实现关注功能
 */
public abstract class AbstractPairOperateStatistics implements PairOperateStatistics {

    @Autowired
    private RedisTemplate<String,Long> longRedisTemplate;

    public abstract String targetKey(long targetId);

    public abstract String sponsorKey(long sponsorId);

    /**
     * 分页查询
     */
    public List<Target> page(long sponsorId, int pageNum, int pageSize) {
        String sponsorKey = sponsorKey(sponsorId);
        long start = (long) (pageNum - 1) * pageSize;
        long end = (long) pageNum * pageSize -1;
        Set<TypedTuple<Long>> rangeWithScores = longRedisTemplate.opsForZSet().rangeWithScores(sponsorKey, start, end);
        return rangeWithScores == null ? new ArrayList<>() : rangeWithScores.stream().map(typedTuple -> {
            Long targetId = typedTuple.getValue();
            Double score = typedTuple.getScore();
            Long timestamp = score == null ? null : score.longValue();
            Target target = new Target();
            target.setTargetId(targetId);
            target.setTimestamp(timestamp);
            return target;
        }).collect(Collectors.toList());
    }

    /**
     * 资源操作时间戳,0表示不存在
     */
    public long selectTarget(long targetId){
        String targetKey = targetKey(targetId);
        Long count = longRedisTemplate.opsForZSet().zCard(targetKey);
        return count != null ? count : 0;
    }

    /**
     * 当前用户对目标资源进行操作时间戳,0表示不存在
     */
    public long selectSponsor(long sponsorId){
        String sponsorKey = sponsorKey(sponsorId);
        Long count = longRedisTemplate.opsForZSet().zCard(sponsorKey);
        return count != null ? count : 0;
    }

    /**
     * 记录操作
     * @return 返回最终操作数
     */
    public int insert(long sponsorId, long targetId){
        String sponsorKey = sponsorKey(sponsorId);
        String targetKey = targetKey(targetId);
        String script = LuaScript.PAIR_INSERT;
        List<String> keys = new ArrayList<>();
        keys.add(sponsorKey);
        keys.add(targetKey);
        Long count = longRedisTemplate.opsForZSet().getOperations().execute(RedisScript.of(script, Long.class), keys, sponsorId, targetId);
        return count != null ? count.intValue() : 0;
    }

    /**
     * 仅删除source的set，uid的set脏数据待用户查询列表时提示已删除
     */
    @Override
    public boolean delete(long targetId) {
        String sourceKey = targetKey(targetId);
        Boolean delete = longRedisTemplate.opsForZSet().getOperations().delete(sourceKey);
        return Objects.equals(delete, true);
    }

}
