package com.heartape.hap.statistics;

import com.heartape.hap.constant.LuaScript;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 资源操作统计，如关注等单次2状态不可重复操作,仅用于点赞
 * 笔记:redis事务只能保证ACID中的隔离性和一致性，无法保证原子性和持久性。如果开启事务不关闭的话,无法获取到还未保存的数据,因为redis事务就是将所有命令一起执行
 * RedisTemplate.setEnableTransactionSupport(true)配置会默认开启事务,自动执行multi命令,直到手动调用exec命令时才会真正顺序去执行
 */
public abstract class AbstractTypeOperateStatistics {

    @Autowired
    private RedisTemplate<String,Long> longRedisTemplate;

    @Autowired
    private RedisTemplate<String,Integer> intRedisTemplate;
    
    public abstract String positiveKey(long targetId);
    public abstract String negativeKey(long targetId);

    public abstract String sponsorKey(long sponsorId);

    /**
     * 积极操作数
     */
    public int selectPositiveNumber(long targetId) {
        String positiveKey = positiveKey(targetId);
        Integer count = intRedisTemplate.opsForValue().get(positiveKey);
        return count == null ? 0 : count;
    }

    /**
     * 消极操作数
     */
    public int selectNegativeNumber(long targetId) {
        String negativeKey = negativeKey(targetId);
        Integer count = intRedisTemplate.opsForValue().get(negativeKey);
        return count == null ? 0 : count;
    }

    /**
     * 记录操作
     */
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
     * 操作类型
     */
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
     */
    public boolean delete(long targetId){
        String positiveKey = positiveKey(targetId);
        String negativeKey = negativeKey(targetId);
        List<String> keys = new ArrayList<>();
        keys.add(positiveKey);
        keys.add(negativeKey);
        Long delete = intRedisTemplate.delete(keys);
        return Objects.equals(delete, 2L);
    }

    public enum TypeEnum {
        /** 积极的 */
        POSITIVE(1),
        /** 消极的 */
        NEGATIVE(-1);

        private final Integer typeCode;

        TypeEnum(Integer typeCode) {
            this.typeCode = typeCode;
        }

        public Integer getTypeCode() {
            return typeCode;
        }

        @Override
        public String toString() {
            return "{typeCode = " + typeCode + "}";
        }
    }

    @Data
    public static class TypeNumber {
        private Integer positive;
        private Integer negative;

        public TypeNumber(Integer positive, Integer negative) {
            this.positive = positive;
            this.negative = negative;
        }
    }

}
