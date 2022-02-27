package com.heartape.hap.oauth.handler;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.heartape.hap.oauth.exception.LoginForbiddenException;
import com.heartape.hap.oauth.feign.LoginFeign;
import com.heartape.hap.oauth.response.Result;
import com.heartape.hap.oauth.entity.HapUserDetails;
import com.heartape.hap.oauth.entity.Visitor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * 登录处理器
 */
@Service
public class HapDetailsService implements ReactiveUserDetailsService {

    /**
     * 不加Lazy启动时会阻塞在获取route
     */
    @Autowired
    @Lazy
    private LoginFeign loginFeign;
    /**
     * 新版spring security保存密码时需要携带{bcrypt},没有的话会报缺少id错误
     */
    @SneakyThrows
    @Override
    public Mono<UserDetails> findByUsername(String s) {
        // 异步运行调用feign
        CompletableFuture<Result> future = CompletableFuture.supplyAsync(() -> loginFeign.login(s));
        Result result = future.join();
        if (result.getCode() != 1) {
            throw new LoginForbiddenException();
        }
        Object data = result.getData();
        String s1 = new Gson().toJson(data);
        // gson解析LocalDateTime失败,改用阿里
        // Visitor visitor = new Gson().fromJson(new Gson().toJson(data), Visitor.class);
        Visitor visitor = JSONObject.parseObject(s1, Visitor.class);
        HapUserDetails hapUserDetails = new HapUserDetails(visitor);
        return Mono.just(hapUserDetails);
    }
}
