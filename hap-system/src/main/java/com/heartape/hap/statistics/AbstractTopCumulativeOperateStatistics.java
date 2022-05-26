package com.heartape.hap.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractTopCumulativeOperateStatistics implements TopCumulativeOperateStatistics {

    @Autowired
    private RedisTemplate<String,Long> longRedisTemplate;

    public abstract String getKey();

    @Override
    public int count(long targetId) {
        String key = getKey();
        Double score = longRedisTemplate.opsForZSet().score(key, targetId);
        return score != null ? score.intValue() : 0;
    }

    /**
     * 统计增加
     * @param targetId 资源id
     * @param delta 增量，必须为正数
     * @return 最终分数
     */
    @Override
    public int updateIncrement(long targetId, int delta) {
        String key = getKey();
        Double score = longRedisTemplate.opsForZSet().incrementScore(key, targetId, delta);
        return score != null ? score.intValue() : 0;
    }

    /**
     * 统计减少
     * @param targetId 资源id
     * @param delta 增量，必须为正数
     * @return 最终分数
     */
    @Override
    public int updateDecrement(long targetId, int delta) {
        String key = getKey();
        Double score = longRedisTemplate.opsForZSet().incrementScore(key, targetId, -delta);
        return score != null ? score.intValue() : 0;
    }

    /**
     * 成倍数地修改资源操作数，用于热度推荐
     * @param targetId 资源id
     * @param multiple 全量倍数，必须为正数 result = data * multiple
     */
    @Override
    public int updateMultiple(long targetId, double multiple) {
        String script = LuaScript.CUMULATIVE_MULTIPLE;
        List<String> keys = new ArrayList<>();
        String key = getKey();
        keys.add(key);
        // 由全量得到增量
        double increment = multiple - 1;
        Long score = longRedisTemplate.opsForZSet().getOperations().execute(RedisScript.of(script, Long.class), keys, targetId, increment);
        return score != null ? score.intValue() : 0;
    }

    @Override
    public List<CumulativeValue> selectPage(int pageNum, int pageSize) {
        String key = getKey();
        long start = (long) (pageNum - 1) * pageSize;
        long end = (long) pageNum * pageSize -1;
        Set<ZSetOperations.TypedTuple<Long>> typedTuples = longRedisTemplate.opsForZSet().rangeWithScores(key, start, end);
        if (CollectionUtils.isEmpty(typedTuples)) {
            return new ArrayList<>();
        }
        return typedTuples.stream().map(typedTuple -> {
            CumulativeValue cumulativeValue = new CumulativeValue();
            cumulativeValue.setResourceId(typedTuple.getValue());
            Double score = typedTuple.getScore();
            int operate = score == null ? 0 : score.intValue();
            cumulativeValue.setOperate(operate);
            return cumulativeValue;
        }).collect(Collectors.toList());
    }

    @Override
    public boolean delete(long targetId) {
        String key = getKey();
        Long remove = longRedisTemplate.opsForZSet().remove(key, targetId);
        return Objects.equals(remove, 1L);
    }
}
