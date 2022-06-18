package com.heartape.hap.constant;

public class HttpConstant {

    /**请求头TOKEN,尽量设置为全部小写,因为规范允许大小写不敏感,反代时会被转为全部小写*/
    public final static String HEADER_TOKEN = "x-token";
    /**token过期时间(天)*/
    public final static int TOKEN_EXPIRE_TIME = 7;
    /** 令牌中的token对应的key */
    public final static String TOKEN_JWT = "token_jwt";
    /** redis的token的key前缀 */
    public final static String TOKEN_KEY_HEADER = "token:";
    /** redis的code的key前缀 */
    public final static String CODE_KEY_HEADER = "code:";

    /**请求头验证码*/
    public final static String HEADER_CODE_ID = "X-Code-Id";
    public final static String HEADER_CODE = "X-Code";
    /**验证码长度*/
    public final static int CODE_LENGTH = 4;
    /**验证码过期时间(秒)*/
    public final static int CODE_EXPIRE_TIME = 60;
}
