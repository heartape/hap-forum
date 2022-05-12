package com.heartape.hap.business.statistics;

import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractCumulativeOperateStatistics implements CumulativeOperateStatistics {

    public abstract ZSetOperations<String, Long> getZSetOperations();

    public abstract String getKey(Long mainId);

    @Override
    public int operateNumber(Long mainId, long sourceId) {
        String sourceKey = getKey(mainId);
        Double score = getZSetOperations().score(sourceKey, sourceId);
        return score != null ? score.intValue() : 0;
    }

    @Override
    public int operateNumberAll(Long mainId) {
        String sourceKey = getKey(mainId);
        List<String> keys = new ArrayList<>();
        keys.add(sourceKey);
        String script = LuaScript.CUMULATIVE_SCORES;
        Long score = getZSetOperations().getOperations().execute(RedisScript.of(script, Long.class), keys);
        return score != null ? score.intValue() : 0;
    }

    /**
     * 统计增加
     * @param sourceId 资源id
     * @param delta 增量，必须为正数
     * @return 最终分数
     */
    @Override
    public int operateIncrement(Long mainId, long sourceId, int delta) {
        String sourceKey = getKey(mainId);
        Double score = getZSetOperations().incrementScore(sourceKey, sourceId, delta);
        return score != null ? score.intValue() : 0;
    }

    /**
     * 统计减少
     * @param sourceId 资源id
     * @param delta 增量，必须为正数
     * @return 最终分数
     */
    @Override
    public int operateDecrement(Long mainId, long sourceId, int delta) {
        String sourceKey = getKey(mainId);
        Double score = getZSetOperations().incrementScore(sourceKey, sourceId, -delta);
        return score != null ? score.intValue() : 0;
    }

    /**
     * 成倍数地修改资源操作数，用于热度推荐
     * @param sourceId 资源id
     * @param multiple 全量倍数，必须为正数 result = data * multiple
     */
    @Override
    public int operateMultiple(Long mainId, long sourceId, double multiple) {
        // lua返回数字时，需要tonumber(score)函数将结果转化成数字，因为lua所有的方法返回值都是string
        String script = LuaScript.CUMULATIVE_MULTIPLE;
        List<String> keys = new ArrayList<>();
        String sourceKey = getKey(mainId);
        keys.add(sourceKey);
        // 由全量得到增量
        double increment = multiple - 1;
        Long score = getZSetOperations().getOperations().execute(RedisScript.of(script, Long.class), keys, sourceId, increment);
        return score != null ? score.intValue() : 0;
    }

    @Override
    public Set<ZSetOperations.TypedTuple<Long>> operateNumberPage(Long mainId, int pageNum, int pageSize) {
        String sourceKey = getKey(mainId);
        long start = (long) (pageNum - 1) * pageSize;
        long end = (long) pageNum * pageSize -1;
        return getZSetOperations().rangeWithScores(sourceKey, start, end);
    }

    @Override
    public boolean removeOperate(Long mainId, long sourceId) {
        String sourceKey = getKey(mainId);
        Long remove = getZSetOperations().remove(sourceKey, sourceId);
        return Objects.equals(remove, 1L);
    }
}
