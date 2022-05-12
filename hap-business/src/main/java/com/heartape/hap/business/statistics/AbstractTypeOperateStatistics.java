package com.heartape.hap.business.statistics;

import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.*;

/**
 * 笔记:redis事务只能保证ACID中的隔离性和一致性，无法保证原子性和持久性。如果开启事务不关闭的话,无法获取到还未保存的数据,因为redis事务就是将所有命令一起执行
 * RedisTemplate.setEnableTransactionSupport(true)配置会默认开启事务,自动执行multi命令,直到手动调用exec命令时才会真正顺序去执行
 */
public abstract class AbstractTypeOperateStatistics implements TypeOperateStatistics {

    public abstract ZSetOperations<String, Long> getZSetOperations();
    
    public abstract String getKeyHeader(long sourceId);
    
    public abstract String getUserKeyHeader(long uid);

    /**
     * 资源积极操作是否被记录
     */
    @Override
    public boolean getPositiveOperate(long sourceId, long uid){
        String sourceKey = getKeyHeader(sourceId);
        Double score = getZSetOperations().score(sourceKey, uid);
        return score != null && Objects.equals(score.intValue(), TypeEnum.POSITIVE.getTypeCode());
    }

    /**
     * 资源消极操作是否被记录
     */
    @Override
    public boolean getNegativeOperate(long sourceId, long uid){
        String sourceKey = getKeyHeader(sourceId);
        Double score = getZSetOperations().score(sourceKey, uid);
        return score != null && Objects.equals(score.intValue(), TypeEnum.NEGATIVE.getTypeCode());
    }

    @Override
    public int getPositiveOperateNumber(long sourceId) {
        String sourceKey = getKeyHeader(sourceId);
        Long count = getZSetOperations().count(sourceKey, TypeEnum.POSITIVE.getTypeCode(), TypeEnum.POSITIVE.getTypeCode());
        return count == null ? 0 : count.intValue();
    }

    @Override
    public int getNegativeOperateNumber(long sourceId) {
        String sourceKey = getKeyHeader(sourceId);
        Long count = getZSetOperations().count(sourceKey, TypeEnum.NEGATIVE.getTypeCode(), TypeEnum.NEGATIVE.getTypeCode());
        return count == null ? 0 : count.intValue();
    }

    /**
     * 记录资源受到的用户积极操作,返回true表示操作成功
     */
    @Override
    public boolean setPositiveOperate(long sourceId, long uid){
        String sourceKey = getKeyHeader(sourceId);
        return setOperate(sourceKey, uid, TypeEnum.POSITIVE);
    }

    /**
     * 记录资源受到的用户消极操作,返回true表示操作成功
     */
    @Override
    public boolean setNegativeOperate(long sourceId, long uid){
        String sourceKey = getKeyHeader(sourceId);
        return setOperate(sourceKey, uid, TypeEnum.NEGATIVE);
    }

    /**
     * 资源操作类型
     */
    @Override
    public String getOperateType(long sourceId, long uid){
        String sourceKey = getKeyHeader(sourceId);
        return getOperateType(sourceKey, uid);
    }

    /**
     * 移除资源受到的用户操作,返回true表示操作成功
     */
    @Override
    public boolean removeOperate(long sourceId, long uid){
        String sourceKey = getKeyHeader(sourceId);
        Long number = getZSetOperations().remove(sourceKey, uid);
        return Objects.equals(number, 1L);
    }

    /**
     * 当前用户是否已经对目标资源进行积极操作
     */
    @Override
    public boolean getPeoplePositiveOperate(long uid, long sourceId){
        String userKey = getUserKeyHeader(uid);
        Double score = getZSetOperations().score(userKey, sourceId);
        return score != null && Objects.equals(score.intValue(), TypeEnum.POSITIVE.getTypeCode());
    }

    /**
     * 当前用户是否已经对目标资源进行消极操作
     */
    @Override
    public boolean getPeopleNegativeOperate(long uid, long sourceId){
        String userKey = getUserKeyHeader(uid);
        Double score = getZSetOperations().score(userKey, sourceId);
        return score != null && Objects.equals(score.intValue(), TypeEnum.NEGATIVE.getTypeCode());
    }

    /**
     * 记录用户对目标资源的积极操作行为,返回true表示操作成功
     */
    @Override
    public boolean setPeoplePositiveOperate(long uid, long sourceId){
        String userKey = getUserKeyHeader(uid);
        return setOperate(userKey, sourceId, TypeEnum.POSITIVE);
    }

    /**
     * 记录用户对目标资源的消极操作行为,返回true表示操作成功
     */
    @Override
    public boolean setPeopleNegativeOperate(long uid, long sourceId){
        String userKey = getUserKeyHeader(uid);
        return setOperate(userKey, sourceId, TypeEnum.NEGATIVE);
    }

    /**
     * 用户对目标资源操作类型
     */
    @Override
    public String getPeopleOperateType(long uid, long sourceId){
        String userKey = getUserKeyHeader(uid);
        return getOperateType(userKey, sourceId);
    }

    /**
     * 移除用户对目标资源的操作行为,返回true表示操作成功
     */
    @Override
    public boolean removePeopleOperate(long uid, long sourceId){
        String userKey = getUserKeyHeader(uid);
        Long remove = getZSetOperations().remove(userKey, sourceId);
        return Objects.equals(remove, 1L);
    }

    /**
     * 记录资源受到的用户操作,返回true表示操作成功
     */
    private boolean setOperate(String key, long member, TypeEnum typeEnum){
        String script = LuaScript.TYPE_SET;
        List<String> keys = new ArrayList<>();
        keys.add(key);
        // 因为redis中小数和
        Boolean success = getZSetOperations().getOperations().execute(RedisScript.of(script, Boolean.class), keys, member, typeEnum.getTypeCode());
        return Objects.equals(success, true);
    }

    /**
     * 资源操作类型
     */
    private String getOperateType(String sourceKey, long member){
        Double score = getZSetOperations().score(sourceKey, member);
        if (score != null && Objects.equals(score.intValue(), TypeEnum.POSITIVE.getTypeCode())) {
            return TypeEnum.POSITIVE.toString();
        } else if (score != null && Objects.equals(score.intValue(), TypeEnum.NEGATIVE.getTypeCode())) {
            return TypeEnum.NEGATIVE.toString();
        } else {
            return TypeEnum.NULL.toString();
        }
    }

}
