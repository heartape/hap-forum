package com.heartape.hap.business.feign;

import com.heartape.hap.business.exception.InterfaceInnerInvokeException;
import com.heartape.hap.business.exception.SystemErrorException;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.utils.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenFeignServiceImpl {

    @Autowired
    private TokenFeignService tokenFeignService;

    @Autowired
    private AssertUtils assertUtils;

    public long getUid() {
//        Result result = tokenFeignService.getUid();
//        String path = "/api/oauth/uid";
//        check(result, path);
//        Object data = result.getData();
//        assertUtils.businessState(data instanceof Long, new InterfaceInnerInvokeException(String.format("远程调用%s接口uid数据类型错误",path)));
//        return (Long) result.getData();
        return 1L;
    }

    private void check(Result result, String path) {
        assertUtils.businessState(result != null && result.getCode() == 1, new InterfaceInnerInvokeException(String.format("远程调用%s接口失败",path)));
        assertUtils.businessState(result.getData() != null, new InterfaceInnerInvokeException(String.format("远程调用%s接口数据为空",path)));
    }
}
