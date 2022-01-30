package com.heartape.hap.security.constant;

public class RedisKeys {

    /** 记录某个用户的登录行为 */
    public static final String loginKey = "photo.login:u:%s:string";
    /** 最大登录次数 */
    public static final int MAX_LOGIN_TIMES = 10;
}
