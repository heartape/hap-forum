package com.heartape.hap.business.statistics;

import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 资源操作记录工具类，如点赞等单次不可重复操作
 * 笔记:redis事务只能保证ACID中的隔离性和一致性，无法保证原子性和持久性。如果开启事务不关闭的话,无法获取到还未保存的数据,因为redis事务就是将所有命令一起执行
 * RedisTemplate.setEnableTransactionSupport(true)配置会默认开启事务,自动执行multi命令,直到手动调用exec命令时才会真正顺序去执行
 */
public abstract class AbstractCumulativeOperateStatistics implements CumulativeOperateStatistics {

    private final String UID = "uid:";

    public abstract ZSetOperations<String, Long> getZSetOperations();

    public abstract String getKey();

    @Override
    public int operateNumber(long sourceId) {
        String sourceKey = getKey();
        Double score = getZSetOperations().score(sourceKey, sourceId);
        return score != null ? score.intValue() : 0;
    }

    /**
     * 统计增加
     * @param sourceId 资源id
     * @param delta 增量，必须为正数
     * @return 最终分数
     */
    @Override
    public int operateIncrement(long sourceId, int delta) {
        String sourceKey = getKey();
        Double score = getZSetOperations().incrementScore(sourceKey, sourceId, delta);
        return score != null ? score.intValue() : 0;
    }

    /**
     * 成倍数地修改资源操作数，用于热度推荐
     * @param sourceId 资源id
     * @param multiple 全量倍数，必须为正数 result = data * multiple
     */
    @Override
    public int operateMultipleIncrement(long sourceId, double multiple) {
        // lua返回数字时，需要tonumber(score)函数将结果转化成数字，因为lua所有的方法返回值都是string
        String script = "local key = KEYS[1]\n local member = ARGV[1]\n local multiple = ARGV[2]\n local score = redis.call(\"zscore\", key, member)\n local type = type(score)\n local delta = score * multiple\n if type == \"string\" then\n score = tonumber(score)\n end\n if (score > 0) then\n local result = redis.call(\"zincrby\", key, delta, member)\n return tonumber(result)\n else\n return 0\n end";
        List<String> keys = new ArrayList<>();
        String sourceKey = getKey();
        keys.add(sourceKey);
        // 由全量得到增量
        double increment = multiple - 1;
        Long score = getZSetOperations().getOperations().execute(RedisScript.of(script, Long.class), keys, sourceId, increment);
        return score != null ? score.intValue() : 0;
    }

    /**
     * 统计减少
     * @param sourceId 资源id
     * @param delta 增量，必须为正数
     * @return 最终分数
     */
    @Override
    public int operateDecrement(long sourceId, int delta) {
        String sourceKey = getKey();
        Double score = getZSetOperations().incrementScore(sourceKey, sourceId, -delta);
        return score != null ? score.intValue() : 0;
    }

    @Override
    public boolean removeOperate(long sourceId) {
        String sourceKey = getKey();
        Long remove = getZSetOperations().remove(sourceKey, sourceId);
        return Objects.equals(remove, 1L);
    }
}
