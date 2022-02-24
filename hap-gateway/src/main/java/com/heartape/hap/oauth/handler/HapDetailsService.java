package com.heartape.hap.oauth.handler;

import com.heartape.hap.oauth.security.HapUserDetails;
import com.heartape.hap.oauth.entity.Visitor;
import com.heartape.hap.oauth.security.HapGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录处理器
 */
@Service
public class HapDetailsService implements ReactiveUserDetailsService {

    /**
     * 新版spring security保存密码时需要携带{bcrypt},没有的话会报缺少id错误
     */
    @Override
    public Mono<UserDetails> findByUsername(String s) {
        Visitor visitor = new Visitor(1L,"123456", "{bcrypt}" + new BCryptPasswordEncoder().encode("123456"),"nickname","avatar","admin","1234567890","12345@qq.com", LocalDateTime.now());
        String role = visitor.getRole();
        HapGrantedAuthority hapGrantedAuthority = new HapGrantedAuthority(role);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(hapGrantedAuthority);
        HapUserDetails hapUserDetails = new HapUserDetails(visitor, roles);
        return Mono.just(hapUserDetails);
    }
}
