package com.heartape.hap.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heartape.hap.security.entity.Visitor;
import com.heartape.hap.security.exception.RoleExistedException;
import com.heartape.hap.security.exception.UsernameExistenceException;
import com.heartape.hap.security.entity.LoginInfo;
import com.heartape.hap.security.entity.LoginUserToken;
import com.heartape.hap.security.entity.RegisterInfo;
import com.heartape.hap.security.mapper.VisitorMapper;
import com.heartape.hap.security.utils.TokenUtil;
import com.heartape.hap.security.service.IVisitorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class VisitorServiceImpl implements IVisitorService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private VisitorMapper visitorMapper;

    @Bean
    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 注册时填好用户名时自动发送ajax请求检查用户名
     */
    @Override
    public void check(String username) {
        QueryWrapper<Visitor> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Visitor visitor = visitorMapper.selectOne(wrapper);
        if (visitor !=null){
            throw new UsernameExistenceException();
        }
    }

    @Override
    public void register(RegisterInfo registerInfo) {
        String [] roles = {"admin", "creator", "viewer"};
        String role = registerInfo.getRole();
        if (!Arrays.asList(roles).contains(role)){
            throw new RoleExistedException();
        }
        String username = registerInfo.getUsername();
        check(username);
        Visitor visitor = new Visitor();
        BeanUtils.copyProperties(registerInfo, visitor);
        String encode = passwordEncoder().encode(registerInfo.getPassword());
        visitor.setPassword(encode);
        visitor.setCreateTime(LocalDateTime.now());
        // todo:默认头像
        visitor.setAvatar("https://gitee.com/heartape/photo-url/tree/master/avatar/1.jpeg");
        visitorMapper.insert(visitor);
    }

    /**
     * 使用账户密码登录的情况
     */
    @Override
    public String login(LoginInfo loginInfo) {
        // TODO:对验证码进行验证

        // 登录账号密码
        String username = loginInfo.getUsername();
        String password = loginInfo.getPassword();

        // 登陆次数
        tokenUtil.loginTimesWithinRange(username);

        // 登陆验证
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        LoginUserToken loginUserToken = (LoginUserToken) authenticate.getPrincipal();
        return tokenUtil.create(loginUserToken);
    }


    @Override
    public void logout() {
        tokenUtil.delete();
    }
}
