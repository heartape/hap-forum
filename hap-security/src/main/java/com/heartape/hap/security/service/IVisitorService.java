package com.heartape.hap.security.service;

import com.heartape.hap.security.entity.LoginInfo;
import com.heartape.hap.security.entity.RegisterInfo;

public interface IVisitorService {

    void check(String username);

    void register(RegisterInfo registerInfo);

    String login(LoginInfo loginInfo);

    void logout();
}
