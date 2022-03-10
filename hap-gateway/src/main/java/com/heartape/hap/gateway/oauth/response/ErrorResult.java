package com.heartape.hap.gateway.oauth.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.heartape.hap.gateway.oauth.exception.BusinessExceptionEnum;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResult {
    /** HTTP状态码 */
    private Integer status;
    /** HTTP异常提示 */
    private String error;
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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;

    public static ErrorResult error(BusinessExceptionEnum exceptionEnum, String path) {
        ErrorResult errorResult = new ErrorResult();
        HttpStatus httpStatus = exceptionEnum.getHttpStatus();
        errorResult.status = httpStatus.value();
        errorResult.error = httpStatus.getReasonPhrase();
        errorResult.code = exceptionEnum.getResultCode().getCode();
        errorResult.message = exceptionEnum.getResultCode().getMessage();
        errorResult.path = path;
        errorResult.timestamp = LocalDateTime.now();
        return errorResult;
    }

    public static ErrorResult error(HttpStatus httpStatus, ResultCode resultCode, String path) {
        ErrorResult errorResult = new ErrorResult();
        errorResult.status = httpStatus.value();
        errorResult.error = httpStatus.getReasonPhrase();
        errorResult.code = resultCode.getCode();
        errorResult.message = resultCode.getMessage();
        errorResult.path = path;
        errorResult.timestamp = LocalDateTime.now();
        return errorResult;
    }
}
