package com.heartape.hap.api.constant;

public enum RoleEnum {

    ADMIN("admin"),
    CREATOR("creator"),
    VIEWER("viewer");

    private final String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
