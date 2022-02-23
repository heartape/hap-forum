package com.heartape.hap.oauth.response;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果格式
 */
@Data
public class ResultMap {
    /**状态码*/
    private Integer code;

    /**消息*/
    private String message;

    /**数据*/
    private Map<String,Object> data = new HashMap<>();

    private ResultMap(){}

    public static ResultMap success(){
        ResultMap r = new ResultMap();
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMessage(ResultCode.SUCCESS.getMessage());
        return r;
    }

    public static ResultMap error(ResultCode resultCode){
        ResultMap r = new ResultMap();
        r.setCode(resultCode.getCode());
        r.setMessage(resultCode.getMessage());
        return r;
    }

    /**
     * 下面的方法都是为链式编程准备
     */
    public ResultMap message(String message){
        this.setMessage(message);
        return this;
    }

    public ResultMap code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResultMap data(Map<String,Object> data){
        this.setData(data);
        return this;
    }

    public ResultMap data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

}
