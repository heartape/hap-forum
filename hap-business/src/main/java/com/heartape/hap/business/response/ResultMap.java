package com.heartape.hap.business.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel(value="统一返回结果格式", description="")
public class ResultMap {
    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "消息")
    private String message;

    @ApiModelProperty(value = "数据")
    private Map<String,Object> data = new HashMap<>();

    private ResultMap(){}

    public static ResultMap success(){
        ResultMap r = new ResultMap();
        r.setCode(ResultCode.SUCCESS.getCode());
        r.setMessage(ResultCode.SUCCESS.getMessage());
        return r;
    }

    /**
     * 链式编程
     */
    public ResultMap data(Map<String,Object> data){
        this.setData(data);
        return this;
    }

    public ResultMap data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

}
