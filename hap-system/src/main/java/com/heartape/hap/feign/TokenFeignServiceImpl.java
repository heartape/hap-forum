package com.heartape.hap.feign;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;
import com.heartape.hap.exception.InterfaceInnerInvokeException;
import com.heartape.hap.response.Result;
import com.heartape.hap.utils.AssertUtils;
import com.heartape.hap.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenFeignServiceImpl {

    @Autowired
    private TokenFeignService tokenFeignService;

    @Autowired
    private AssertUtils assertUtils;

    public long getUid() {
        String token = HttpUtils.getToken(HttpUtils.getRequest());
        Result result = tokenFeignService.getUid(token);
        String path = "/api/oauth/uid";
        check(result, path);
        String data = new Gson().toJson(result.getData());
        return JSONObject.parseObject(data, Long.class);
    }

    public String getNickname(Long uid) {
        Result result = tokenFeignService.getNickname(uid);
        String path = "/api/creator/info/nickname";
        check(result, path);
        String data = new Gson().toJson(result.getData());
        return JSONObject.parseObject(data, String.class);
    }

    public HapUserDetails getTokenInfo() {
        String token = HttpUtils.getToken(HttpUtils.getRequest());
        Result result = tokenFeignService.getTokenInfo(token);
        String path = "/api/oauth/token";
        check(result, path);
        String data = new Gson().toJson(result.getData());
        return JSONObject.parseObject(data, HapUserDetails.class);
    }

    private void check(Result result, String path) {
        String message1 = "远程调用接口失败, path=" + path;
        assertUtils.businessState(result != null && result.getCode() == 1, new InterfaceInnerInvokeException(message1));
        String message2 = "远程调用接口数据为空, path=" + path;
        assertUtils.businessState(result.getData() != null, new InterfaceInnerInvokeException(message2));
    }
}
