package com.heartape.hap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heartape.hap.constant.AccountStatusEnum;
import com.heartape.hap.constant.RoleEnum;
import com.heartape.hap.entity.Creator;
import com.heartape.hap.entity.dto.CreatorDTO;
import com.heartape.hap.mapper.CreatorMapper;
import com.heartape.hap.service.IRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Slf4j
public class RegisterServiceImpl implements IRegisterService {

    @Autowired
    private CreatorMapper creatorMapper;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void checkEmail(String email) {
        // todo:验证次数统计并限制
        boolean exists = creatorMapper.exists(new QueryWrapper<Creator>().eq("email", email));
        log.info("邮箱:" + email + "已存在");
        Assert.isTrue(!exists, "邮箱已存在");
        // todo:发送邮箱验证码
    }

    @Override
    public void checkPhone(String mobile) {
        // todo:验证次数统计并限制
        boolean exists = creatorMapper.exists(new QueryWrapper<Creator>().eq("mobile", mobile));
        log.info("手机号:" + mobile + "已存在");
        Assert.isTrue(!exists, "手机号已存在");
        // todo:发送短信验证码
    }

    @Override
    public void register(CreatorDTO creatorDTO) {
        String email = creatorDTO.getEmail();
        String mobile = creatorDTO.getMobile();
        // todo:注册次数统计并限制
        // 检查注册信息
        boolean exists = creatorMapper.exists(new QueryWrapper<Creator>().eq("email", email).eq("mobile", mobile));
        log.info("邮箱:" + email + ",手机号:" + mobile);
        Assert.isTrue(!exists, "邮箱:" + email + ",或手机号:" + mobile + "已存在");

        // 进行注册
        String password = creatorDTO.getPassword();
        String encodePassword = passwordEncoder.encode(password);

        Creator creator = new Creator();
        BeanUtils.copyProperties(creatorDTO, creator);
        creator.setPassword(encodePassword);
        creator.setRole(RoleEnum.CREATOR.getName());
        creator.setAccountStatus(AccountStatusEnum.NORMAL.getCode());
        creator.setProfile("这个人很懒,没有个人简介");
        creator.setAvatar("https://file.heartape.com/picture/avatar-1.jpg");
        creatorMapper.insert(creator);

    }
}
