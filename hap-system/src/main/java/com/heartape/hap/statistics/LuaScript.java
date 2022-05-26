package com.heartape.hap.statistics;

public class LuaScript {

    /**
     * zscore在元素不存在时，返回false
     */
    public final static String PAIR_INSERT = "local sponsorKey = KEYS[1] local targetKey = KEYS[2] local sponsorId = ARGV[1] local targetId = ARGV[2] local data1 = redis.call(\"zscore\", targetKey, sponsorId) local data2 = redis.call(\"zscore\", sponsorKey, targetId) if data1 ~= false then if data2 ~= false then redis.call(\"zrem\", targetKey, sponsorId) redis.call(\"zrem\", sponsorKey, targetId) return redis.call(\"zcard\", targetKey) else redis.call(\"zrem\", targetKey, sponsorId) return redis.call(\"zcard\", targetKey) end else if data2 ~= false then redis.call(\"zrem\", sponsorKey, targetId) return redis.call(\"zcard\", targetKey) else local timestamp = redis.call(\"TIME\")[1] redis.call(\"zadd\", targetKey, timestamp, sponsorId) redis.call(\"zadd\", sponsorKey, timestamp, targetId) return redis.call(\"zcard\", targetKey) end end";

    public final static String TYPE_INSERT_POSITIVE = "local sponsorKey = KEYS[1]\n local positiveKey = KEYS[2]\n local negativeKey = KEYS[3]\n local member = ARGV[1]\n local positiveScore = ARGV[2]\n local negativeScore = ARGV[3]\n local result = {}\n local selectScore = redis.call(\"zscore\", sponsorKey, member)\n if (selectScore == positiveScore) then\n redis.call(\"zrem\", sponsorKey, member)\n result[1] = redis.call(\"decr\", positiveKey)\n local negative = redis.call(\"get\", negativeKey)\n result[2] = negative == false and 0 or negative\n return result\n elseif (selectScore == negativeScore) then\n result[1] = redis.call(\"incr\", positiveKey)\n result[2] = redis.call(\"decr\", negativeKey)\n redis.call(\"zadd\", sponsorKey, positiveScore, member)\n return result\n else\n result[1] = redis.call(\"incr\", positiveKey)\n local negative = redis.call(\"get\", negativeKey)\n result[2] = negative == false and 0 or negative\n redis.call(\"zadd\", sponsorKey, positiveScore, member)\n return result end";
    public final static String TYPE_INSERT_NEGATIVE = "local sponsorKey = KEYS[1]\n local positiveKey = KEYS[2]\n local negativeKey = KEYS[3]\n local member = ARGV[1]\n local positiveScore = ARGV[2]\n local negativeScore = ARGV[3]\n local result = {}\n local selectScore = redis.call(\"zscore\", sponsorKey, member)\n if (selectScore == negativeScore) then\n redis.call(\"zrem\", sponsorKey, member)\n result[2] = redis.call(\"decr\", negativeKey)\n local positive = redis.call(\"get\", positiveKey)\n result[1] = positive == false and 0 or positive\n return result\n elseif (selectScore == positiveScore) then\n result[2] = redis.call(\"incr\", negativeKey)\n result[1] = redis.call(\"decr\", positiveKey)\n redis.call(\"zadd\", sponsorKey, negativeScore, member)\n return result\n else\n result[2] = redis.call(\"incr\", negativeKey)\n local positive = redis.call(\"get\", positiveKey)\n result[1] = positive == false and 0 or positive\n redis.call(\"zadd\", sponsorKey, negativeScore, member)\n return result end";

    /**
     * lua返回数字时，需要tonumber(score)函数将结果转化成数字，因为lua所有的方法返回值都是string
     */
    public final static String CUMULATIVE_MULTIPLE = "local key = KEYS[1]\n local member = ARGV[1]\n local multiple = ARGV[2]\n local score = redis.call(\"zscore\", key, member)\n local type = type(score)\n local delta = score * multiple\n if type == \"string\" then\n score = tonumber(score)\n end\n if (score > 0) then\n local result = redis.call(\"zincrby\", key, delta, member)\n return tonumber(result)\n else\n return 0\n end";

    public final static String MASTER_SLAVE_INSERT = "local masterKey = KEYS[1] local slaveKey = KEYS[2] local slaveId = ARGV[1] local data = redis.call(\"zscore\", masterKey, slaveId) if data == false then local result = redis.call(\"incr\", slaveKey) local timestamp = redis.call(\"TIME\")[1] redis.call(\"zadd\", masterKey, timestamp, slaveId) return result else redis.call(\"zrem\", masterKey, slaveId) return redis.call(\"decr\", slaveKey) end";

    public final static String MASTER_SLAVE_DELETE = "local masterKey = KEYS[1] local slaveKey = KEYS[2] local slaveId = ARGV[1] local data = redis.call(\"zscore\", masterKey, slaveId) if data == false then return false else redis.call(\"zrem\", masterKey, slaveId) redis.call(\"decr\", slaveKey) return true end";

    // zset scores求和
    // public final static String Z_SET_SCORES_TOTAL = "local key = KEYS[1]\n local ret = 0\n local data = redis.call('zrange', key, 0, -1, 'withscores')\n for i=1, #data, 2 do\n ret = ret + data[i+1]\n end\n return ret";
}
