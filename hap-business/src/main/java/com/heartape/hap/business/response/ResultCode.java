package com.heartape.hap.business.response;

public enum ResultCode {

    SUCCESS(1,"操作成功"),
    /** 参数 */
    PARAM_IS_INVALID(10001,"参数无效"),
    PARAM_IS_BLANK(10002,"参数为空"),
    PARAM_TYPE_BIND_ERROR(10003,"参数类型错误"),
    PARAM_NOT_COMPLETE(10004,"参数缺失"),
    /** 用户 */
    USER_NOT_LOGGED_IN(20001,"用户未登录"),
    USER_LOGIN_ERROR(20002,"账号不存在或密码错误"),
    USER_TOKEN_ERROR(20003,"令牌认证失败"),
    USER_ACCOUNT_FORBIDDEN(20004,"账号已被禁用"),
    USER_LOGIN_FORBIDDEN(20005,"账号已被暂时禁止登录"),
    USER_NOT_EXIST(20006,"用户不存在"),
    USER_HAS_EXISTED(20007,"用户已存在"),
    LOGIN_CREDENTIAL(20008,"凭证已存在"),
    /** 业务 */
    SPECIFIED_QUESTIONED_USER_NOT_EXIST(30001,"业务错误"),
    /** 系统 */
    SYSTEM_INNER_ERROR(40001,"系统繁忙"),
    /** 数据 */
    RESULT_DATA_NONE(50001,"数据未找到"),
    DATA_IS_WRONG(50002,"数据错误"),
    DATA_ALREADY_EXISTED(50003,"数据冲突"),
    RELY_DATA_NOT_EXISTED(50004,"依赖的数据不存在或已被删除"),
    RESOURCE_OPERATE_REPEAT(50005,"数据重复操作"),
    /** 接口 */
    INTERFACE_INNER_INVOKE_ERROR(60001,"内部接口异常"),
    INTERFACE_OUTER_INVOKE_ERROR(60002,"外部接口异常"),
    INTERFACE_FORBID_VISIT(60003,"禁止访问"),
    INTERFACE_ADDRESS_INVALID(60004,"地址无效"),
    INTERFACE_REQUEST_TIMEOUT(60005,"请求超时"),
    INTERFACE_EXCEED_LOAD(60006,"当前请求较多"),
    /** 权限 */
    PERMISSION_NO_ACCESS(70001,"无访问权限"),
    RESOURCE_EXISTED(70002,"资源冲突"),
    RESOURCE_NOT_EXISTED(70003,"资源不存在"),
    ROLE_NO_EXISTED(70004,"角色不存在"),
    PERMISSION_NO_REMOVE(70005,"无删除权限");

    private final Integer code;

    private final String message;

    ResultCode(Integer code, String message) {
        this.code=code;
        this.message=message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
