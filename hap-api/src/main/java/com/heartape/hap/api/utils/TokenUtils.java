package com.heartape.hap.api.utils;

import com.heartape.hap.api.entity.LoginCode;
import com.heartape.hap.oauth.entity.HapUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * token操作工具类
 * 1.创建token
 * 2.查找token并更新token时间,用于登录状态验证
 * 3.删除token,退出登录
 */
@Component
public class TokenUtils {

    @Autowired
    private RedisTemplate<String, HapUserDetails> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CodeUtils codeUtils;

    @Value("${token.secret}")
    private String secret;

    /**请求头*/
    @Value("${token.header}")
    private String header;

    /**登录验证码有效时间*/
    @Value("${code.expireTime}")
    private Integer codeExpireTime;

    /** 令牌中的token对应的key */
    private final String TOKEN_KEY = "token_key";
    /** redis的code的key前缀 */
    private final String CODE_KEY_HEADER = "hap.code:%s:string";

    /**
     * 新的验证码
     */
    public LoginCode newCode() {
        String code = "123456";
        String codeId = UUID.randomUUID().toString();
        String codeKey = String.format(CODE_KEY_HEADER, codeId);
        stringRedisTemplate.opsForValue().set(codeKey,code,codeExpireTime, TimeUnit.SECONDS);
        // todo: 将code转化为随即图片并返回

        String picture = "";
        try {
            picture = codeUtils.createPicture(code);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new LoginCode(codeId, picture);
    }

    /**
     * 校验验证码
     */
    public boolean checkCode(LoginCode loginCode) {
        String codeKey = String.format(CODE_KEY_HEADER, loginCode.getCodeId());
        String codeValue = stringRedisTemplate.opsForValue().get(codeKey);
        return StringUtils.hasText(codeValue) && codeValue.equals(loginCode.getCode());
    }

    /**
     * 给token解签名
     */
    public Claims parseAutograph(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getToken(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(header);
    }

    /**
     * token解签名后获取redis key
     */
    public String getTokenKey(Claims claims) {
        return (String) claims.get(TOKEN_KEY);
    }

    /**
     * token解签名后获取redis key
     */
    public HapUserDetails getTokenValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取用户信息
     */
    public HapUserDetails getUserDetails() {
        String token = getToken(getRequest());
        Claims claims = parseAutograph(token);
        String tokenKey = getTokenKey(claims);
        return getTokenValue(tokenKey);
    }

    /**
     * 获取用户id
     */
    public long getUid(){
        String token = getToken(getRequest());
        Claims claims = parseAutograph(token);
        String tokenKey = getTokenKey(claims);
        HapUserDetails userDetails = getTokenValue(tokenKey);
        return userDetails.getUid();
    }

    /**
     * 获取request
     */
    public HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new RuntimeException();
        }
        return attributes.getRequest();
    }

    /**
     * 获取request
     */
    public HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new RuntimeException();
        }
        return attributes.getResponse();
    }

}