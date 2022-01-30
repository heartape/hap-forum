package com.heartape.hap.security.utils;

import com.heartape.hap.security.constant.RedisKeys;
import com.heartape.hap.security.exception.LoginForbiddenException;
import com.heartape.hap.security.entity.LoginUserToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * token操作工具类
 * 1.创建token
 * 2.查找token并更新token时间,用于登录状态验证
 * 3.删除token,退出登录
 */
@Component
public class TokenUtil {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private RedisTemplate<String,Integer> intRedisTemplate;

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expireTime}")
    private Integer expireTime;

    @Value("${token.header}")
    private String header;

    private final String LOGIN_USER_KEY = "login_user_key";
    private final String TOKEN_KEY_HEADER = "photo.token:";

    public String create(LoginUserToken loginUserToken){
        String tokenKey = TOKEN_KEY_HEADER + UUID.randomUUID();
        loginUserToken.setToken(tokenKey);
        redisTemplate.opsForValue().set(tokenKey, loginUserToken, expireTime, TimeUnit.DAYS);
        return autograph(tokenKey);
    }

    public void delete(){
        // 获取在请求头中的token
        String token = HttpRequestUtil.getRequest().getHeader(header);
        // 将token解签名
        String uuid = parseAutograph(token);
        redisTemplate.delete(uuid);
    }

    /**
     * 用于springSecurity的退出控制器
     */
    public void delete(HttpServletRequest httpServletRequest){
        // 获取在请求头中的token
        String token = httpServletRequest.getHeader(this.header);
        if (token!=null){
            // 将token解签名
            String uuid = parseAutograph(token);
            redisTemplate.delete(uuid);
        }
    }

    /**
     * 给token签名
     */
    private String autograph(String uuid){
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(LOGIN_USER_KEY, uuid);
        String token = Jwts
                .builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return token;
    }

    /**
     * 给token解签名
     */
    private String parseAutograph(String token) {
        Claims claims = null;
        try {
            claims = Jwts
                    .parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new SignatureException("登录信息无效");
        }
        String uuid = (String) claims.get(LOGIN_USER_KEY);
        return uuid;
    }

    public LoginUserToken getInfo(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader(this.header);
        if (token==null){
            return null;
        }
        // 将token解签名
        String uuid = parseAutograph(token);
        Object o = redisTemplate.opsForValue().get(uuid);
        return (LoginUserToken) redisTemplate.opsForValue().get(uuid);
    }

    public LoginUserToken getInfo(){
        HttpServletRequest httpServletRequest = HttpRequestUtil.getRequest();
        String token = httpServletRequest.getHeader(this.header);
        if (token==null){
            throw new RuntimeException("登录信息已过期");
        }
        // 将token解签名
        String uuid = parseAutograph(token);
        return (LoginUserToken) redisTemplate.opsForValue().get(uuid);
    }

    public long getUid(){
        return getInfo().getUid();
    }

    /**
     * 用户10分钟之内最多进行10次登录
     */
    public void loginTimesWithinRange(String username) {
        String login = String.format(RedisKeys.loginKey, username);

        Integer times = intRedisTemplate.opsForValue().get(login);
        if (times == null) {
            intRedisTemplate.opsForValue().set(login,1,10, TimeUnit.MINUTES);
        } else if (times > 0 && times < RedisKeys.MAX_LOGIN_TIMES) {
            intRedisTemplate.opsForValue().increment(login);
        } else {
            throw new LoginForbiddenException();
        }
    }

}
