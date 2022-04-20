package com.heartape.hap.api.service;

import com.heartape.hap.api.entity.Creator;

public interface ITokenService {

    Creator mailPasswordLogin(String email);
}
