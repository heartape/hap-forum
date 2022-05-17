package com.heartape.hap.business.statistics;

public class LuaScript {

    /**
     * zscore在元素不存在时，返回false
     */
    public final static String OPERATE_SET = "local sourceKey = KEYS[1] local userKey = KEYS[2] local uid = ARGV[1] local sourceId = ARGV[2] local timestamp = ARGV[3] local data1 = redis.call(\"zscore\", sourceKey, uid) local data2 = redis.call(\"zscore\", userKey, sourceId) if data1 ~= false then if data2 ~= false then redis.call(\"zrem\", sourceKey, uid) redis.call(\"zrem\", userKey, sourceId) return false else redis.call(\"zrem\", sourceKey, uid) return false end else if data2 ~= false then redis.call(\"zrem\", userKey, sourceId) return false else redis.call(\"zadd\", sourceKey, timestamp, uid) redis.call(\"zadd\", userKey, timestamp, sourceId) return true end end";

    public final static String TYPE_SET = "local key = KEYS[1]\n local member = ARGV[1]\n local score = ARGV[2]\n if (redis.call(\"zscore\", key, member) ~= score) then\n redis.call(\"zadd\", key, score, member)\n return true\n else\n redis.call(\"zrem\", key, member)\n return false\n end";

    public final static String CUMULATIVE_MULTIPLE = "local key = KEYS[1]\n local member = ARGV[1]\n local multiple = ARGV[2]\n local score = redis.call(\"zscore\", key, member)\n local type = type(score)\n local delta = score * multiple\n if type == \"string\" then\n score = tonumber(score)\n end\n if (score > 0) then\n local result = redis.call(\"zincrby\", key, delta, member)\n return tonumber(result)\n else\n return 0\n end";

    public final static String CUMULATIVE_SCORES = "local key = KEYS[1]\n local ret = 0\n local data = redis.call('zrange', key, 0, -1, 'withscores')\n for i=1, #data, 2 do\n ret = ret + data[i+1]\n end\n return ret";
}
