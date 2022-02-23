package com.heartape.hap.oauth.utils;

import com.heartape.hap.oauth.constant.RedisKeys;
import com.heartape.hap.oauth.entity.HapUserDetails;
import com.heartape.hap.oauth.exception.LoginForbiddenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
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
    private Integer expireTime;

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
     * redis的key前缀
     */
    private final String TOKEN_KEY_HEADER = "hap.token:";

    public String create(HapUserDetails hapUserDetails){
        String tokenKey = TOKEN_KEY_HEADER + UUID.randomUUID();
        redisTemplate.opsForValue().set(tokenKey, hapUserDetails, expireTime, TimeUnit.DAYS);
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
     * 获取用户信息
     */
    public HapUserDetails getInfo(ServerWebExchange exchange) {
        String token = getToken(exchange);
        Claims claims = parseAutograph(token);
        String tokenKey = getTokenKey(claims);
        return getTokenValue(tokenKey);
    }

    public long getUid(ServerWebExchange exchange){
        String token = getToken(exchange);
        Claims claims = parseAutograph(token);
        String tokenKey = getTokenKey(claims);
        HapUserDetails userDetails = getTokenValue(tokenKey);
        return userDetails.getUid();
    }

//    public void delete(){
//        // 获取在请求头中的token
//        String token = HttpRequestUtil.getRequest().getHeader(header);
//        // 将token解签名
//        String uuid = parseAutograph(token);
//        redisTemplate.delete(uuid);
//    }

    /**
     * 用于springSecurity的退出控制器
     */
//    public void delete(HttpServletRequest httpServletRequest){
//        // 获取在请求头中的token
//        String token = httpServletRequest.getHeader(this.header);
//        if (token!=null){
//            // 将token解签名
//            String uuid = parseAutograph(token);
//            redisTemplate.delete(uuid);
//        }
//    }

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
