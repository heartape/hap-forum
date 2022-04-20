package com.heartape.hap.api.service;

import com.heartape.hap.api.entity.dto.CreatorDTO;

public interface IRegisterService {

    void checkEmail(String email);

    void checkPhone(String mobile);

    void register(CreatorDTO creatorDTO);
}
