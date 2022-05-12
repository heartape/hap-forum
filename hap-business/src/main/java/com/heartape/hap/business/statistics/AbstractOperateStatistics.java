package com.heartape.hap.business.statistics;

import org.springframework.data.redis.core.SetOperations;

import java.util.Objects;

public abstract class AbstractOperateStatistics implements OperateStatistics {

    public abstract SetOperations<String, Long> getSetOperations();

    public abstract String getKeyHeader();

    public abstract String getUserKeyHeader();

    /**
     * 资源操作是否被记录
     */
    public boolean getOperate(long sourceId, long uid){
        String sourceKey = getKeyHeader() + sourceId;
        Boolean isMember = getSetOperations().isMember(sourceKey, uid);
        return isMember != null && isMember;
    }

    /**
     * 记录资源受到的用户操作,返回true表示操作成功
     */
    public boolean setOperate(long sourceId, long uid){
        String sourceKey = getKeyHeader() + sourceId;
        Long number = getSetOperations().add(sourceKey, uid);
        return Objects.equals(number, 1L);
    }

    /**
     * 移除资源受到的用户操作,返回true表示操作成功
     */
    public boolean removeOperate(long sourceId, long uid){
        String sourceKey = getKeyHeader() + sourceId;
        Long number = getSetOperations().remove(sourceKey, uid);
        return Objects.equals(number, 1L);
    }

    /**
     * 当前用户是否已经对目标资源进行操作
     */
    public boolean getPeopleOperate(long uid, long sourceId){
        String userKey = getUserKeyHeader();
        Boolean member = getSetOperations().isMember(userKey, sourceId);
        return member != null && member;
    }

    /**
     * 记录用户对目标资源的操作行为,返回true表示操作成功
     */
    public boolean setPeopleOperate(long uid, long sourceId){
        String userKey = getUserKeyHeader();
        Long add = getSetOperations().add(userKey, sourceId);
        return add != null && add == 1;
    }

    /**
     * 移除用户对目标资源的操作行为,返回true表示操作成功
     */
    public boolean removePeopleOperate(long uid, long sourceId){
        String userKey = getUserKeyHeader();
        Long remove = getSetOperations().remove(userKey, sourceId);
        return remove != null && remove == 1;
    }
}
