package com.heartape.hap.business.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(value="统一返回结果格式", description="")
public class Result {
    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "数据")
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

    public Result data(String key, Object value){
        if (this.data == null) {
            Map<String, Object> data = new HashMap<>();
            data.put(key, value);
            this.data = data;
        } else if (this.data instanceof Map) {
            ((Map<String, Object>) this.data).put(key, value);
        }
        return this;
    }

    public Object getData() {
        return data;
    }
}
