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
    
    public abstract String getSponsorKeyHeader(long sponsorId);

    /**
     * 资源积极操作是否被记录
     */
    @Override
    public boolean getPositiveOperate(long sourceId, long sponsorId){
        String sourceKey = getKeyHeader(sourceId);
        Double score = getZSetOperations().score(sourceKey, sponsorId);
        return score != null && Objects.equals(score.intValue(), TypeEnum.POSITIVE.getTypeCode());
    }

    /**
     * 资源消极操作是否被记录
     */
    @Override
    public boolean getNegativeOperate(long sourceId, long sponsorId){
        String sourceKey = getKeyHeader(sourceId);
        Double score = getZSetOperations().score(sourceKey, sponsorId);
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
    public boolean setPositiveOperate(long sourceId, long sponsorId){
        String sourceKey = getKeyHeader(sourceId);
        return setOperate(sourceKey, sponsorId, TypeEnum.POSITIVE);
    }

    /**
     * 记录资源受到的用户消极操作,返回true表示操作成功
     */
    @Override
    public boolean setNegativeOperate(long sourceId, long sponsorId){
        String sourceKey = getKeyHeader(sourceId);
        return setOperate(sourceKey, sponsorId, TypeEnum.NEGATIVE);
    }

    /**
     * 资源操作类型
     */
    @Override
    public String getOperateType(long sourceId, long sponsorId){
        String sourceKey = getKeyHeader(sourceId);
        return getOperateType(sourceKey, sponsorId);
    }

    /**
     * 移除资源受到的用户操作,返回true表示操作成功
     */
    @Override
    public boolean removeOperate(long sourceId, long sponsorId){
        String sourceKey = getKeyHeader(sourceId);
        Long number = getZSetOperations().remove(sourceKey, sponsorId);
        return Objects.equals(number, 1L);
    }

    /**
     * 当前用户是否已经对目标资源进行积极操作
     */
    @Override
    public boolean getPeoplePositiveOperate(long sponsorId, long sourceId){
        String sponsorKey = getSponsorKeyHeader(sponsorId);
        Double score = getZSetOperations().score(sponsorKey, sourceId);
        return score != null && Objects.equals(score.intValue(), TypeEnum.POSITIVE.getTypeCode());
    }

    /**
     * 当前用户是否已经对目标资源进行消极操作
     */
    @Override
    public boolean getPeopleNegativeOperate(long sponsorId, long sourceId){
        String sponsorKey = getSponsorKeyHeader(sponsorId);
        Double score = getZSetOperations().score(sponsorKey, sourceId);
        return score != null && Objects.equals(score.intValue(), TypeEnum.NEGATIVE.getTypeCode());
    }

    /**
     * 记录用户对目标资源的积极操作行为,返回true表示操作成功
     */
    @Override
    public boolean setPeoplePositiveOperate(long sponsorId, long sourceId){
        String sponsorKey = getSponsorKeyHeader(sponsorId);
        return setOperate(sponsorKey, sourceId, TypeEnum.POSITIVE);
    }

    /**
     * 记录用户对目标资源的消极操作行为,返回true表示操作成功
     */
    @Override
    public boolean setPeopleNegativeOperate(long sponsorId, long sourceId){
        String sponsorKey = getSponsorKeyHeader(sponsorId);
        return setOperate(sponsorKey, sourceId, TypeEnum.NEGATIVE);
    }

    /**
     * 用户对目标资源操作类型
     */
    @Override
    public String getPeopleOperateType(long sponsorId, long sourceId){
        String sponsorKey = getSponsorKeyHeader(sponsorId);
        return getOperateType(sponsorKey, sourceId);
    }

    /**
     * 移除用户对目标资源的操作行为,返回true表示操作成功
     */
    @Override
    public boolean removePeopleOperate(long sponsorId, long sourceId){
        String sponsorKey = getSponsorKeyHeader(sponsorId);
        Long remove = getZSetOperations().remove(sponsorKey, sourceId);
        return Objects.equals(remove, 1L);
    }

    /**
     * 记录资源受到的用户操作,
     * 返回true表示添加操作，false表示取消操作
     */
    private boolean setOperate(String key, long member, TypeEnum typeEnum){
        String script = LuaScript.TYPE_SET;
        List<String> keys = new ArrayList<>();
        keys.add(key);
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
