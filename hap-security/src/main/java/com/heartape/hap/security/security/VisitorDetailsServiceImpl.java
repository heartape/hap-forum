package com.heartape.hap.security.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heartape.hap.security.entity.LoginUserToken;
import com.heartape.hap.security.entity.Visitor;
import com.heartape.hap.security.mapper.VisitorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisitorDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private VisitorMapper visitorMapper;
    @Override
    public UserDetails loadUserByUsername(String s) {
        QueryWrapper<Visitor> wrapper = new QueryWrapper<>();
        wrapper.eq("username", s).eq("status", "1");
        Visitor visitor = visitorMapper.selectOne(wrapper);
        String role = visitor.getRole();
        MyGrantedAuthority myGrantedAuthority = new MyGrantedAuthority(role);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(myGrantedAuthority);
        return new LoginUserToken(visitor,roles);
    }
}
