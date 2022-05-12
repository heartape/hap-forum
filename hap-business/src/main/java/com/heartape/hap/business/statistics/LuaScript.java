package com.heartape.hap.business.statistics;

public class LuaScript {

    public final static String TYPE_SET = "local key = KEYS[1]\n local member = ARGV[1]\n local score = ARGV[2]\n if (redis.call(\"zscore\", key, member) ~= score) then\n redis.call(\"zadd\", key, score, member)\n \treturn true\n else\n return false\n end";

    public final static String CUMULATIVE_MULTIPLE = "local key = KEYS[1]\n local member = ARGV[1]\n local multiple = ARGV[2]\n local score = redis.call(\"zscore\", key, member)\n local type = type(score)\n local delta = score * multiple\n if type == \"string\" then\n score = tonumber(score)\n end\n if (score > 0) then\n local result = redis.call(\"zincrby\", key, delta, member)\n return tonumber(result)\n else\n return 0\n end";

    public final static String CUMULATIVE_SCORES = "local key = KEYS[1]\n local ret = 0\n local data = redis.call('zrange', key, 0, -1, 'withscores')\n for i=1, #data, 2 do\n ret = ret + data[i+1]\n end\n return ret";
}
