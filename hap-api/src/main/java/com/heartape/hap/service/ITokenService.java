package com.heartape.hap.service;

import com.heartape.hap.entity.Creator;

public interface ITokenService {

    Creator mailPasswordLogin(String email);
}
