package com.heartape.hap.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.heartape.hap.entity.Creator;
import com.heartape.hap.entity.HapUserDetails;
import com.heartape.hap.feign.OauthFeign;
import com.heartape.hap.utils.TokenUtils;
import com.heartape.hap.exception.LoginErrorException;
import com.heartape.hap.response.Result;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * 密码登录处理器
 */
@Service
public class HapDetailsService implements ReactiveUserDetailsService {

    /**
     * 不加Lazy启动时会阻塞在获取route
     */
    @Autowired
    @Lazy
    private OauthFeign oauthFeign;

    @Autowired
    @Lazy
    private TokenUtils tokenUtils;
    /**
     * 新版spring security默认的加密算法保存密码时需要携带{bcrypt},没有的话会报缺少id错误
     */
    @SneakyThrows
    @Override
    public Mono<UserDetails> findByUsername(String s) {
        if (!tokenUtils.checkMail(s)) {
            throw new LoginErrorException("登录用户名不是邮件格式,邮箱:" + s);
        }
        // 异步运行调用feign
        CompletableFuture<Result> future = CompletableFuture.supplyAsync(() -> oauthFeign.mailPasswordLogin(s));
        Result result = future.join();
        if (result.getCode() != 1) {
            return Mono.empty();
        }
        Object data = result.getData();
        String s1 = new Gson().toJson(data);
        Creator creator = JSONObject.parseObject(s1, Creator.class);
        HapUserDetails hapUserDetails = new HapUserDetails(creator);
        return Mono.just(hapUserDetails);
    }
}
