package com.heartape.hap.statistics;

import com.heartape.hap.constant.LuaScript;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 主从统计，用于实现收藏夹
 */
public abstract class AbstractMasterSlaveOperateStatistics {

    @Autowired
    private RedisTemplate<String,Long> longRedisTemplate;

    @Autowired
    private RedisTemplate<String,Integer> intRedisTemplate;

    public abstract String masterKey(long masterId);

    public abstract String slaveKey(long slaveId);

    /**
     * slave数据查询
     */
    public long total(long masterId){
        String masterKey = masterKey(masterId);
        Long size = longRedisTemplate.opsForZSet().size(masterKey);
        return size == null ? 0 : size;
    }

    /**
     * 检查是否超出范围
     */
    public boolean check(int total, int pageNum, int pageSize) {
        return total > (pageNum - 1) * pageSize;
    }

    /**
     * 分页查询,查询前先进行total和pageNum的校验，不在范围内就不进行查询
     */
    public List<Slave> select(long masterId, int pageNum, int pageSize){
        String masterKey = masterKey(masterId);
        long start = (long) (pageNum - 1) * pageSize;
        long end = (long) pageNum * pageSize -1;
        Set<ZSetOperations.TypedTuple<Long>> rangeWithScores = longRedisTemplate.opsForZSet().rangeWithScores(masterKey, start, end);
        return rangeWithScores == null ? new ArrayList<>() : rangeWithScores.stream().map(typedTuple -> {
            Long value = typedTuple.getValue();
            Double score = typedTuple.getScore();
            Long timestamp = score == null ? null : score.longValue();
            Slave slave = new Slave();
            slave.setSlaveId(value);
            slave.setTimestamp(timestamp);
            return slave;
        }).collect(Collectors.toList());
    }

    /**
     * slave数据查询
     */
    public long slaveCount(long slaveId){
        String slaveKey = slaveKey(slaveId);
        Long size = intRedisTemplate.opsForValue().size(slaveKey);
        return size == null ? 0 : size;
    }

    /**
     * 记录操作，若已记录操作，则取消已记录的操作,返回slave数据
     */
    public long insert(long masterId, long slaveId){
        String masterKey = masterKey(masterId);
        String slaveKey = slaveKey(slaveId);
        String script = LuaScript.MASTER_SLAVE_INSERT;
        List<String> keys = new ArrayList<>();
        keys.add(masterKey);
        keys.add(slaveKey);
        Long number = longRedisTemplate.execute(RedisScript.of(script, Long.class), keys, slaveId);
        return number == null ? 0 : number;
    }

    /**
     * 删除master记录，slave-1
     * @return true:执行删除,false:slaveId在masterId中不存在
     */
    public boolean delete(long masterId, long slaveId) {
        String masterKey = masterKey(masterId);
        String slaveKey = slaveKey(slaveId);
        String script = LuaScript.MASTER_SLAVE_DELETE;
        List<String> keys = new ArrayList<>();
        keys.add(masterKey);
        keys.add(slaveKey);
        Boolean delete = longRedisTemplate.opsForZSet().getOperations().execute(RedisScript.of(script, Boolean.class), keys, slaveId);
        return Objects.equals(delete, true);
    }

    /**
     * 移除slave，master不做操作，查询到已删除slave时标识为已删除
     */
    public boolean remove(long slaveId) {
        String slaveKey = slaveKey(slaveId);
        Boolean delete = intRedisTemplate.opsForValue().getOperations().delete(slaveKey);
        return Objects.equals(delete, true);
    }

    @Data
    public static class Slave {
        private Long slaveId;
        private Long timestamp;
    }

}
