package com.heartape.hap.business.feign;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;
import com.heartape.hap.business.exception.InterfaceInnerInvokeException;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.utils.AssertUtils;
import com.heartape.hap.business.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenFeignServiceImpl {

    @Autowired
    private TokenFeignService tokenFeignService;

    @Autowired
    private AssertUtils assertUtils;

    @Autowired
    private HttpUtils httpUtils;

    public long getUid() {
        String token = httpUtils.getToken(httpUtils.getRequest());
        Result result = tokenFeignService.getUid(token);
        String path = "/api/oauth/uid";
        check(result, path);
        String data = new Gson().toJson(result.getData());
        return JSONObject.parseObject(data, Long.class);
    }

    public HapUserDetails getTokenInfo() {
        String token = httpUtils.getToken(httpUtils.getRequest());
        Result result = tokenFeignService.getTokenInfo(token);
        String path = "/api/oauth/token";
        check(result, path);
        String data = new Gson().toJson(result.getData());
        return JSONObject.parseObject(data, HapUserDetails.class);
    }

    private void check(Result result, String path) {
        assertUtils.businessState(result != null && result.getCode() == 1, new InterfaceInnerInvokeException(String.format("远程调用%s接口失败",path)));
        assertUtils.businessState(result.getData() != null, new InterfaceInnerInvokeException(String.format("远程调用%s接口数据为空",path)));
    }
}
