package com.heartape.hap.security.security;

import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

@EqualsAndHashCode
public final class MyGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = -7860378898935753318L;
    private String role;

    public MyGrantedAuthority() {
    }

    public MyGrantedAuthority(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    public String getAuthority() {
        // 角色默认需要ROLE_前缀
        return "ROLE_" + this.role;
    }

    public String toString() {
        return this.role;
    }
}
