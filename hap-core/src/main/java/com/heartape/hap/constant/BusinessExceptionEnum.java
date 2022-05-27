package com.heartape.hap.constant;

import com.heartape.hap.response.ResultCode;
import org.springframework.http.HttpStatus;

public enum BusinessExceptionEnum {

    /** 参数 */
    PARAM_IS_INVALID(HttpStatus.BAD_REQUEST,ResultCode.PARAM_IS_INVALID),
    PARAM_IS_BLANK(HttpStatus.BAD_REQUEST,ResultCode.PARAM_IS_BLANK),
    PARAM_TYPE_BIND_ERROR(HttpStatus.BAD_REQUEST,ResultCode.PARAM_TYPE_BIND_ERROR),
    PARAM_NOT_COMPLETE(HttpStatus.BAD_REQUEST,ResultCode.PARAM_NOT_COMPLETE),
    /** 用户 */
    USER_NOT_LOGGED_IN(HttpStatus.BAD_REQUEST,ResultCode.USER_NOT_LOGGED_IN),
    USER_LOGIN_VERIFICATION_CODE_ERROR(HttpStatus.BAD_REQUEST,ResultCode.USER_VERIFICATION_CODE_ERROR),
    USER_LOGIN_ERROR(HttpStatus.BAD_REQUEST,ResultCode.USER_LOGIN_ERROR),
    USER_TOKEN_ERROR(HttpStatus.BAD_REQUEST,ResultCode.USER_TOKEN_ERROR),
    USER_ACCOUNT_FORBIDDEN(HttpStatus.BAD_REQUEST,ResultCode.USER_ACCOUNT_FORBIDDEN),
    USER_LOGIN_FORBIDDEN(HttpStatus.BAD_REQUEST,ResultCode.USER_LOGIN_FORBIDDEN),
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST,ResultCode.USER_NOT_EXIST),
    USER_HAS_EXISTED(HttpStatus.BAD_REQUEST,ResultCode.USER_HAS_EXISTED),
    LOGIN_CREDENTIAL(HttpStatus.BAD_REQUEST,ResultCode.LOGIN_CREDENTIAL),
    /** 业务 */
    SPECIFIED_QUESTIONED_USER_NOT_EXIST(HttpStatus.BAD_REQUEST,ResultCode.SPECIFIED_QUESTIONED_USER_NOT_EXIST),
    /** 系统 */
    SYSTEM_INNER_ERROR(HttpStatus.BAD_REQUEST,ResultCode.SYSTEM_INNER_ERROR),
    /** 数据 */
    RESULT_DATA_NONE(HttpStatus.BAD_REQUEST,ResultCode.RESULT_DATA_NONE),
    DATA_IS_WRONG(HttpStatus.BAD_REQUEST,ResultCode.DATA_IS_WRONG),
    DATA_ALREADY_EXISTED(HttpStatus.BAD_REQUEST,ResultCode.DATA_ALREADY_EXISTED),
    RELY_DATA_NOT_EXISTED(HttpStatus.BAD_REQUEST,ResultCode.RELY_DATA_NOT_EXISTED),
    RESOURCE_OPERATE_REPEAT(HttpStatus.BAD_REQUEST,ResultCode.RESOURCE_OPERATE_REPEAT),
    /** 接口 */
    INTERFACE_INNER_INVOKE_ERROR(HttpStatus.BAD_REQUEST,ResultCode.INTERFACE_INNER_INVOKE_ERROR),
    INTERFACE_OUTER_INVOKE_ERROR(HttpStatus.BAD_REQUEST,ResultCode.INTERFACE_OUTER_INVOKE_ERROR),
    INTERFACE_FORBID_VISIT(HttpStatus.BAD_REQUEST,ResultCode.INTERFACE_FORBID_VISIT),
    INTERFACE_ADDRESS_INVALID(HttpStatus.BAD_REQUEST,ResultCode.INTERFACE_ADDRESS_INVALID),
    INTERFACE_REQUEST_TIMEOUT(HttpStatus.BAD_REQUEST,ResultCode.INTERFACE_REQUEST_TIMEOUT),
    INTERFACE_EXCEED_LOAD(HttpStatus.BAD_REQUEST,ResultCode.INTERFACE_EXCEED_LOAD),
    /** 权限 */
    PERMISSION_NO_ACCESS(HttpStatus.BAD_REQUEST,ResultCode.PERMISSION_NO_ACCESS),
    RESOURCE_EXISTED(HttpStatus.BAD_REQUEST,ResultCode.RESOURCE_EXISTED),
    RESOURCE_NOT_EXISTED(HttpStatus.BAD_REQUEST,ResultCode.RESOURCE_NOT_EXISTED),
    ROLE_NO_EXISTED(HttpStatus.BAD_REQUEST,ResultCode.ROLE_NO_EXISTED),
    PERMISSION_NO_REMOVE(HttpStatus.BAD_REQUEST,ResultCode.PERMISSION_NO_REMOVE);

    private final HttpStatus httpStatus;

    private final ResultCode resultCode;

    BusinessExceptionEnum(HttpStatus httpStatus, ResultCode resultCode) {
        this.httpStatus = httpStatus;
        this.resultCode = resultCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
