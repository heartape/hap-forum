package com.heartape.hap.gateway.oauth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
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
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String role;
    private String accountStatus;

    public HapUserDetails() {
    }

    public HapUserDetails(Creator creator) {
        this.loginTime = LocalDateTime.now();
        this.uid = creator.getUid();
        this.username = creator.getEmail();
        this.nickname = creator.getNickname();
        this.password = creator.getPassword();
        this.avatar = creator.getAvatar();
        this.role = creator.getRole();
        this.accountStatus = creator.getAccountStatus();
    }

    /**
     * redis序列化时会将所有的getter方法视为一个成员变量,需要忽略
     */
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HapGrantedAuthority hapGrantedAuthority = new HapGrantedAuthority(role);
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(hapGrantedAuthority);
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
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return "1".equals(accountStatus);
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return "1".equals(accountStatus);
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return "1".equals(accountStatus);
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return "1".equals(accountStatus);
    }
}
