package com.heartape.hap.business.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.*;

/**
 * 笔记:redis事务只能保证ACID中的隔离性和一致性，无法保证原子性和持久性。如果开启事务不关闭的话,无法获取到还未保存的数据,因为redis事务就是将所有命令一起执行
 * RedisTemplate.setEnableTransactionSupport(true)配置会默认开启事务,自动执行multi命令,直到手动调用exec命令时才会真正顺序去执行
 */
public abstract class AbstractTypeOperateStatistics implements TypeOperateStatistics {

    @Autowired
    private RedisTemplate<String,Long> longRedisTemplate;

    @Autowired
    private RedisTemplate<String,Integer> intRedisTemplate;
    
    public abstract String positiveKey(long targetId);
    public abstract String negativeKey(long targetId);

    public abstract String sponsorKey(long sponsorId);

    @Override
    public int selectPositiveNumber(long targetId) {
        String positiveKey = positiveKey(targetId);
        Integer count = intRedisTemplate.opsForValue().get(positiveKey);
        return count == null ? 0 : count;
    }

    @Override
    public int selectNegativeNumber(long targetId) {
        String negativeKey = negativeKey(targetId);
        Integer count = intRedisTemplate.opsForValue().get(negativeKey);
        return count == null ? 0 : count;
    }

    /**
     * 记录操作
     */
    @Override
    public TypeNumber insert(long sponsorId, long targetId, TypeEnum typeEnum){
        String sponsorKey = sponsorKey(sponsorId);
        String positiveKey = positiveKey(targetId);
        String negativeKey = negativeKey(targetId);

        String script;
        if (typeEnum.equals(TypeEnum.POSITIVE)) {
            script = LuaScript.TYPE_INSERT_POSITIVE;
        } else if (typeEnum.equals(TypeEnum.NEGATIVE)) {
            script = LuaScript.TYPE_INSERT_NEGATIVE;
        } else {
            throw new IllegalArgumentException("操作类型不正确，TypeEnum=" + typeEnum);
        }
        List<String> keys = new ArrayList<>();
        keys.add(sponsorKey);
        keys.add(positiveKey);
        keys.add(negativeKey);
        List<Long> result = longRedisTemplate.opsForZSet().getOperations().execute(RedisScript.of(script, List.class), keys, targetId, TypeEnum.POSITIVE.getTypeCode(), TypeEnum.NEGATIVE.getTypeCode());
        if (result == null) {
            throw new ArrayIndexOutOfBoundsException("查询结果为空");
        }
        int positive = result.get(0).intValue();
        int negative = result.get(1).intValue();
        return new TypeNumber(positive, negative);
    }

    /**
     * 资源操作类型
     */
    @Override
    public TypeEnum type(long sponsorId, long targetId){
        String sponsorKey = sponsorKey(sponsorId);
        Double score = longRedisTemplate.opsForZSet().score(sponsorKey, targetId);
        if (score != null && Objects.equals(score.intValue(), TypeEnum.POSITIVE.getTypeCode())) {
            return TypeEnum.POSITIVE;
        } else if (score != null && Objects.equals(score.intValue(), TypeEnum.NEGATIVE.getTypeCode())) {
            return TypeEnum.NEGATIVE;
        } else {
            return null;
        }
    }

    /**
     * 移除资源
     * todo:部分删除失败时再次执行删除可能会删除正常数据
     */
    @Override
    public boolean delete(long targetId, long sponsorId){
        String positiveKey = positiveKey(targetId);
        String negativeKey = negativeKey(targetId);
        List<String> keys = new ArrayList<>();
        keys.add(positiveKey);
        keys.add(negativeKey);
        Long delete = intRedisTemplate.delete(keys);
        return Objects.equals(delete, 2L);
    }

}
