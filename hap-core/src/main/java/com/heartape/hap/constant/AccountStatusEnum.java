package com.heartape.hap.constant;

/**
 * 账号状态
 */
public enum AccountStatusEnum {

    /** 正常 */
    NORMAL("normal", "1"),
    /** 封停 */
    SUSPENDED("suspended", "0");

    private final String name;
    private final String code;
    /** 未知状态 */
    private final static String UNKNOWN = "unknown";

    AccountStatusEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public static String exchange(String code) {
        if (NORMAL.code.equals(code)) {
            return NORMAL.name;
        } else if (SUSPENDED.code.equals(code)) {
            return SUSPENDED.name;
        } else {
            return UNKNOWN;
        }
    }
}
