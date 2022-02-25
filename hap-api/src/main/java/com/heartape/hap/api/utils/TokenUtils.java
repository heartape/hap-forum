package com.heartape.hap.api.utils;

import com.heartape.hap.oauth.security.HapUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

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

    @Value("${token.secret}")
    private String secret;

    /**
     * 请求头
     */
    @Value("${token.header}")
    private String header;

    /**
     * 令牌中的token对应的key
     */
    private final String TOKEN_KEY = "token_key";

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
    public HapUserDetails getInfo() {
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
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new RuntimeException();
        }
        return attributes.getRequest();
    }

}
