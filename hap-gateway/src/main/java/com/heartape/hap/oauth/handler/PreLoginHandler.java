package com.heartape.hap.oauth.handler;

import com.heartape.hap.oauth.constant.OauthConstant;
import com.heartape.hap.oauth.entity.LoginCode;
import com.heartape.hap.oauth.exception.LoginForbiddenException;
import com.heartape.hap.oauth.exception.SystemErrorException;
import com.heartape.hap.oauth.exception.VerificationCodeErrorException;
import com.heartape.hap.oauth.feign.OauthFeign;
import com.heartape.hap.oauth.response.Result;
import com.heartape.hap.oauth.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 密码登录时对比验证码
 */
@Component
@Slf4j
public class PreLoginHandler implements WebFilter {

    @Autowired
    @Lazy
    private OauthFeign oauthFeign;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        if ("/login".equals(path)) {
            List<String> codeIds = exchange.getRequest().getHeaders().getValuesAsList(OauthConstant.CODE_ID_HEADER_NAME);
            List<String> codes = exchange.getRequest().getHeaders().getValuesAsList(OauthConstant.CODE_HEADER_NAME);
            if (codeIds.isEmpty() || codes.isEmpty()) {
                throw new LoginForbiddenException("验证码或验证码id为空");
            }
            String codeId = codeIds.get(0);
            String code = codes.get(0);
            if (!StringUtils.hasText(codeId) || !StringUtils.hasText(code)) {
                throw new LoginForbiddenException("验证码或验证码id为空");
            } else if (code.length() < OauthConstant.CODE_LENGTH) {
                throw new LoginForbiddenException("验证码长度不符合规定");
            }
            CompletableFuture<Result> future = CompletableFuture.supplyAsync(() -> oauthFeign.checkCode(new LoginCode(codeId, code)));
            Result result = future.join();
            if (!result.getCode().equals(ResultCode.SUCCESS.getCode())) {
                throw new SystemErrorException("调用验证码远程接口异常");
            }
            if (!(Boolean) result.getData()) {
                throw new VerificationCodeErrorException("登录验证码错误,拒绝登录");
            }
        }
        return chain.filter(exchange);
    }
}
