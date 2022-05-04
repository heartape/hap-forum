package com.heartape.hap.business.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 资源操作记录工具类，如点赞等单次不可重复操作
 * 笔记:redis事务只能保证ACID中的隔离性和一致性，无法保证原子性和持久性。如果开启事务不关闭的话,无法获取到还未保存的数据,因为redis事务就是将所有命令一起执行
 * RedisTemplate.setEnableTransactionSupport(true)配置会默认开启事务,自动执行multi命令,直到手动调用exec命令时才会真正顺序去执行
 */
@Component
public class SourceOperateStatistics {

    @Autowired
    private RedisTemplate<String,Long> longRedisTemplate;

    private String keyHeader;

    private final String UID = "uid";

    /**
     * 资源操作是否被记录
     */
    public boolean getOperate(long sourceId, long uid){
        String sourceKey = keyHeader + sourceId;
        Boolean isMember = longRedisTemplate.opsForSet().isMember(sourceKey, uid);
        return isMember != null && isMember;
    }

    /**
     * 记录资源受到的用户操作,返回true表示操作成功
     */
    public boolean setOperate(long sourceId, long uid){
        String sourceKey = keyHeader + sourceId;
        Long number = longRedisTemplate.opsForSet().add(sourceKey, uid);
        return Objects.equals(number, 1L);
    }

    /**
     * 移除资源受到的用户操作,返回true表示操作成功
     */
    public boolean removeOperate(long sourceId, long uid){
        String sourceKey = keyHeader + sourceId;
        Long number = longRedisTemplate.opsForSet().remove(sourceKey, uid);
        return Objects.equals(number, 1L);
    }

    /**
     * 当前用户是否已经对目标资源进行操作
     */
    public boolean getPeopleOperate(long uid, long sourceId){
        String userKey = keyHeader + UID + uid;
        Boolean member = longRedisTemplate.opsForSet().isMember(userKey, sourceId);
        return member != null && member;
    }

    /**
     * 记录用户对目标资源的操作行为,返回true表示操作成功
     */
    public boolean setPeopleOperate(long uid, long sourceId){
        String userKey = keyHeader + UID + uid;
        Long add = longRedisTemplate.opsForSet().add(userKey, sourceId);
        return add != null && add == 1;
    }

    /**
     * 移除用户对目标资源的操作行为,返回true表示操作成功
     */
    public boolean removePeopleOperate(long uid, long sourceId){
        String userKey = keyHeader + UID + uid;
        Long remove = longRedisTemplate.opsForSet().remove(userKey, sourceId);
        return remove != null && remove == 1;
    }
}
