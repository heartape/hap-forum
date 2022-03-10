package com.heartape.hap.gateway.oauth.utils;

import com.heartape.hap.gateway.oauth.constant.RedisKeys;
import com.heartape.hap.gateway.oauth.exception.LoginForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;
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
    private RedisTemplate<String,Integer> intRedisTemplate;

    /** 请求头 */
    @Value("${token.header}")
    private String header;

    /**
     * 检查是否是正确的邮箱格式
     */
    public boolean checkMail(String mail) {
        String format = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        Pattern pattern = Pattern.compile(format);
        Matcher matcher = pattern.matcher(mail);
        return matcher.matches();
    }

    public String getToken(ServerWebExchange exchange) {
        List<String> headers = exchange.getRequest().getHeaders().get(header);
        if (CollectionUtils.isEmpty(headers)) {
            return null;
        }
        return headers.get(0);
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
