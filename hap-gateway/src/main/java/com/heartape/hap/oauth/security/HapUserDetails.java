package com.heartape.hap.oauth.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.heartape.hap.oauth.entity.Visitor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 保存登录用户信息
 */
@Data
public class HapUserDetails implements UserDetails {

    private static final long serialVersionUID = -6185345344674471111L;

    /**登录时间*/
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;

    private Long uid;
    private String password;
    private String username;
    private String nickname;
    private String avatar;
    private String role;
    private Set<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public HapUserDetails() {
    }

    public HapUserDetails(Visitor visitor, List<GrantedAuthority> roles) {
        this.loginTime = LocalDateTime.now();
        this.authorities = new HashSet<>(roles);
        this.uid = visitor.getUid();
        this.username = visitor.getUsername();
        this.nickname = visitor.getNickname();
        this.password = visitor.getPassword();
        this.avatar = visitor.getAvatar();
        this.role = visitor.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
