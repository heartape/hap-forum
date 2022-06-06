package com.heartape.hap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heartape.hap.constant.AccountStatusEnum;
import com.heartape.hap.constant.RoleEnum;
import com.heartape.hap.entity.Creator;
import com.heartape.hap.entity.dto.CreatorPhoneDTO;
import com.heartape.hap.entity.dto.CreatorEmailDTO;
import com.heartape.hap.exception.RegisterUserExistedException;
import com.heartape.hap.mapper.CreatorMapper;
import com.heartape.hap.service.IRegisterService;
import com.heartape.hap.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Autowired
    private AssertUtils assertUtils;

    @Override
    public void checkEmail(String email) {
        // todo:验证次数统计并限制
        boolean exists = creatorMapper.exists(new QueryWrapper<Creator>().eq("email", email));
        assertUtils.businessState(exists, new RegisterUserExistedException("邮箱已存在：" + email));
        // todo:发送邮箱验证码
    }

    @Override
    public void checkPhone(String mobile) {
        // todo:验证次数统计并限制
        boolean exists = creatorMapper.exists(new QueryWrapper<Creator>().eq("mobile", mobile));
        assertUtils.businessState(exists, new RegisterUserExistedException("手机已存在：" + mobile));
        // todo:发送短信验证码
    }

    @Override
    public void registerEmail(CreatorEmailDTO creatorDTO) {
        String email = creatorDTO.getEmail();
        // todo:注册次数统计并限制
        // 检查注册信息
        LambdaQueryWrapper<Creator> queryWrapper = new QueryWrapper<Creator>().lambda();
        queryWrapper.eq(Creator::getEmail, email);
        boolean exists = creatorMapper.exists(queryWrapper);
        assertUtils.businessState(exists, new RegisterUserExistedException("邮箱已存在：" + email));
        // todo:校验验证码
        String code = creatorDTO.getCode();
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

    @Override
    public void registerPhone(CreatorPhoneDTO creatorPhoneDTO) {
        String mobile = creatorPhoneDTO.getMobile();
        // todo:注册次数统计并限制
        // 检查注册信息
        LambdaQueryWrapper<Creator> queryWrapper = new QueryWrapper<Creator>().lambda();
        queryWrapper.eq(Creator::getMobile, mobile);
        boolean exists = creatorMapper.exists(queryWrapper);
        assertUtils.businessState(exists, new RegisterUserExistedException("手机已存在：" + mobile));
        // todo:校验验证码
        String code = creatorPhoneDTO.getCode();

        Creator creator = new Creator();
        BeanUtils.copyProperties(creatorPhoneDTO, creator);
        creator.setRole(RoleEnum.CREATOR.getName());
        creator.setAccountStatus(AccountStatusEnum.NORMAL.getCode());
        creator.setProfile("这个人很懒,没有个人简介");
        creator.setAvatar("https://file.heartape.com/picture/avatar-1.jpg");
        creatorMapper.insert(creator);
    }
}
