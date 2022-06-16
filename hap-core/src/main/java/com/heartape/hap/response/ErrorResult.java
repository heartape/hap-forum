package com.heartape.hap.response;

import com.heartape.hap.constant.BusinessExceptionEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResult {
    /** 系统内部状态码 */
    private Integer code;
    /** 异常精简信息 */
    private String message;
    /** 异常请求路径 */
    private String path;
    /** 异常请求返回数据 */
    private Object data;
    /** 随机数，防止重放攻击 */
    private String random;
    /** 时间戳，防止重放攻击 */
    private LocalDateTime timestamp;

    public static ErrorResult error(BusinessExceptionEnum exceptionEnum, String path) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.code = exceptionEnum.getResultCode().getCode();
        errorResult.message = exceptionEnum.getResultCode().getMessage();
        errorResult.path = path;
        errorResult.timestamp = LocalDateTime.now();
        return errorResult;
    }

    public static ErrorResult error(ResultCode resultCode, String path) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.code = resultCode.getCode();
        errorResult.message = resultCode.getMessage();
        errorResult.path = path;
        errorResult.timestamp = LocalDateTime.now();
        return errorResult;
    }
}
