package com.heartape.hap.oauth.utils;

import com.heartape.hap.oauth.constant.RedisKeys;
import com.heartape.hap.oauth.entity.HapUserDetails;
import com.heartape.hap.oauth.exception.LoginForbiddenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private RedisTemplate<String,Integer> intRedisTemplate;

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expireTime}")
    private Integer tokenExpireTime;

    /** 请求头 */
    @Value("${token.header}")
    private String header;

    /**
     * 令牌中的token对应的key
     */
    private final String TOKEN_KEY = "token_key";
    /**
     * redis的token的key前缀
     */
    private final String TOKEN_KEY_HEADER = "hap.token:";

    /**
     * 检查是否是正确的邮箱格式
     */
    public boolean checkMail(String mail) {
        String format = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }

    public String create(HapUserDetails hapUserDetails){
        String tokenKey = TOKEN_KEY_HEADER + UUID.randomUUID();
        redisTemplate.opsForValue().set(tokenKey, hapUserDetails, tokenExpireTime, TimeUnit.DAYS);
        return autograph(tokenKey);
    }

    /**
     * 给token签名
     */
    private String autograph(String uuid){
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_KEY, uuid);
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
    public Claims parseAutograph(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getToken(ServerWebExchange exchange) {
        List<String> headers = exchange.getRequest().getHeaders().get(header);
        if (CollectionUtils.isEmpty(headers)) {
            return null;
        }
        return headers.get(0);
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
     * 用于springSecurity的退出控制器
     */
    public void delete(ServerWebExchange exchange){
        String token = getToken(exchange);
        Claims claims = parseAutograph(token);
        String tokenKey = getTokenKey(claims);
        redisTemplate.delete(tokenKey);
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
            throw new LoginForbiddenException("用户连续登录超过10次");
        }
    }

}
