package com.heartape.hap.api.utils;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.heartape.hap.api.entity.LoginCode;
import com.heartape.hap.api.entity.RO.LoginCodeRO;
import com.heartape.hap.api.entity.HapUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
import java.util.HashMap;
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
    private StringRedisTemplate stringRedisTemplate;

    @Value("${token.secret}")
    private String secret;

    @Value("${token.expireTime}")
    private Integer tokenExpireTime;

    /**请求头*/
    @Value("${token.header}")
    private String header;

    /**登录验证码有效时间*/
    @Value("${code.expireTime}")
    private Integer codeExpireTime;

    /** 令牌中的token对应的key */
    private final String TOKEN_KEY = "token_key";
    /** redis的code的key前缀 */
    private final String TOKEN_KEY_HEADER = "hap.token:%s:string";
    /** redis的code的key前缀 */
    private final String CODE_KEY_HEADER = "hap.code:%s:string";

    /**
     * 新的验证码
     */
    public LoginCode newCode() {
        String codeId = UUID.randomUUID().toString();
        // 创建图片验证码,定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(80, 40, 4, 2);
        // base64格式前加上 data:image/jpg;base64,/
        String imageBase64Data = shearCaptcha.getImageBase64Data();
        String code = shearCaptcha.getCode();
        String codeKey = String.format(CODE_KEY_HEADER, codeId);
        stringRedisTemplate.opsForValue().set(codeKey,code,codeExpireTime, TimeUnit.SECONDS);
        return new LoginCode(codeId, imageBase64Data);
    }

    /**
     * 校验验证码
     */
    public boolean checkCode(LoginCodeRO loginCode) {
        String codeKey = String.format(CODE_KEY_HEADER, loginCode.getCodeId());
        String codeValue = stringRedisTemplate.opsForValue().get(codeKey);
        return StringUtils.hasText(codeValue) && codeValue.equals(loginCode.getCode());
    }

    /**
     * 检查是否是正确的邮箱格式
     */
    public boolean checkMail(String mail) {
        String format = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
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

    /**
     * 创建token
     */
    public String create(HapUserDetails hapUserDetails){
        String tokenKey = String.format(TOKEN_KEY_HEADER,UUID.randomUUID());
        redisTemplate.opsForValue().set(tokenKey, hapUserDetails, tokenExpireTime, TimeUnit.DAYS);
        return autograph(tokenKey);
    }

    /**
     * 用于springSecurity的退出控制器
     */
    public void delete(HttpServletRequest httpServletRequest){
        String token = getToken(httpServletRequest);
        Claims claims = parseAutograph(token);
        String tokenKey = getTokenKey(claims);
        redisTemplate.delete(tokenKey);
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
    public HapUserDetails getUserDetails(String token) {
        // String token = getToken(getRequest());
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
