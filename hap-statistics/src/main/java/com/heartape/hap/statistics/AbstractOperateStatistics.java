package com.heartape.hap.statistics;

import com.heartape.hap.constant.LuaScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractOperateStatistics {

    @Autowired
    private RedisTemplate<String,Long> longRedisTemplate;

    public abstract String key(long sponsorId);

    /**
     * @param sponsorId 资源
     * @param resourceId 发起方
     * @return 是否操作
     */
    public boolean select(long sponsorId, long resourceId){
        String sponsorKey = key(sponsorId);
        Boolean member = longRedisTemplate.opsForSet().isMember(sponsorKey, resourceId);
        return Objects.equals(member, true);
    }

    /**
     * @param sponsorId 资源
     * @return 操作数
     */
    public long count(long sponsorId){
        String sponsorKey = key(sponsorId);
        Long size = longRedisTemplate.opsForSet().size(sponsorKey);
        return size == null ? 0 : size;
    }

    /**
     * 记录操作，若已记录操作，则取消已记录的操作
     * @param sponsorId 资源
     * @param resourceId 发起方
     * @return 返回操作后数量
     */
    public long insert(long sponsorId, long resourceId){
        String sponsorKey = key(sponsorId);
        // TODO
        String script = LuaScript.PAIR_INSERT;
        List<String> keys = new ArrayList<>();
        keys.add(sponsorKey);
        Long number = longRedisTemplate.opsForSet().getOperations().execute(RedisScript.of(script, Long.class), keys, resourceId);
        return number == null ? 0 : number;
    }

    /**
     * 删除统计，仅注销账号时适用
     */
    public boolean remove(long sponsorId) {
        String sponsorKey = key(sponsorId);
        Boolean delete = longRedisTemplate.opsForSet().getOperations().delete(sponsorKey);
        return Objects.equals(delete, true);
    }

}
