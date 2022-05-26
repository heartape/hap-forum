package com.heartape.hap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heartape.hap.constant.AccountStatusEnum;
import com.heartape.hap.entity.Creator;
import com.heartape.hap.mapper.CreatorMapper;
import com.heartape.hap.service.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements ITokenService {

    @Autowired
    private CreatorMapper creatorMapper;

    @Override
    public Creator mailPasswordLogin(String email) {
        QueryWrapper<Creator> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email).eq("account_status", AccountStatusEnum.NORMAL.getCode()).eq("status", true);
        Creator creator = creatorMapper.selectOne(wrapper);
        // 转换帐号状态
        String accountStatusCode = creator.getAccountStatus();
        String accountStatus = AccountStatusEnum.exchange(accountStatusCode);
        creator.setAccountStatus(accountStatus);
        return creator;
    }

}
