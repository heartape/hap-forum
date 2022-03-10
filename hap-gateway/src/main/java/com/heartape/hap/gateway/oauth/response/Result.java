package com.heartape.hap.gateway.oauth.response;

import lombok.Data;

/**
 * 统一返回结果格式
 */
@Data
public class Result {
    /**状态码*/
    private Integer code;

    /**消息*/
    private String message;

    /**数据*/
    private Object data;

    private Result(){

    }

    public static Result success(){
        Result r = new Result();
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMessage(ResultCode.SUCCESS.getMessage());
        return r;
    }

    public static Result success(Object data){
        Result r = new Result();
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMessage(ResultCode.SUCCESS.getMessage());
        r.setData(data);
        return r;
    }
}
