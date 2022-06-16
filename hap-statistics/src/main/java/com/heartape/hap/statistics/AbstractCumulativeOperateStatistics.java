package com.heartape.hap.statistics;

import com.heartape.hap.constant.LuaScript;
import lombok.Data;
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

/**
 * 资源累计操作统计，如热度
 */
public abstract class AbstractCumulativeOperateStatistics {

    @Autowired
    private RedisTemplate<String,Long> longRedisTemplate;

    public abstract String getKey(long mainId);

    /**
     * 操作数
     */
    public int count(long mainId, long targetId) {
        String key = getKey(mainId);
        Double score = longRedisTemplate.opsForZSet().score(key, targetId);
        return score != null ? score.intValue() : 0;
    }

    /**
     * 统计增加
     * @param targetId 资源id
     * @param delta 增量
     * @return 最终分数
     */
    public int updateIncrement(long mainId, long targetId, int delta) {
        String key = getKey(mainId);
        Double score = longRedisTemplate.opsForZSet().incrementScore(key, targetId, delta);
        return score != null ? score.intValue() : 0;
    }

    /**
     * 成倍数地修改资源操作数，用于热度推荐
     * @param targetId 资源id
     * @param multiple 全量倍数，必须为正数 result = data * multiple
     */
    public int updateMultiple(long mainId, long targetId, double multiple) {
        if (multiple <= 0) {
            throw new IllegalArgumentException("倍数应该大于0,实际为" + multiple);
        }
        // lua返回数字时，需要tonumber(score)函数将结果转化成数字，因为lua所有的方法返回值都是string
        String script = LuaScript.CUMULATIVE_MULTIPLE;
        List<String> keys = new ArrayList<>();
        String key = getKey(mainId);
        keys.add(key);
        // 由全量得到增量
        double increment = multiple - 1;
        Long score = longRedisTemplate.opsForZSet().getOperations().execute(RedisScript.of(script, Long.class), keys, targetId, increment);
        return score != null ? score.intValue() : 0;
    }

    /**
     * 分页查询
     * @param mainId 父级id
     */
    public List<CumulativeValue> selectPage(long mainId, int pageNum, int pageSize) {
        String key = getKey(mainId);
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

    /**
     * 删除父级
     * @param mainId 父级id
     */
    public boolean delete(long mainId) {
        String key = getKey(mainId);
        Boolean delete = longRedisTemplate.opsForZSet().getOperations().delete(key);
        return Objects.equals(delete, true);
    }

    /**
     * 删除目标
     * @param mainId 父级id
     * @param targetId 目标id
     */
    public boolean delete(long mainId, long targetId) {
        String key = getKey(mainId);
        Long remove = longRedisTemplate.opsForZSet().remove(key, targetId);
        return Objects.equals(remove, 1L);
    }

    @Data
    public static class CumulativeValue {
        private Long resourceId;
        private Integer operate;
    }
}
