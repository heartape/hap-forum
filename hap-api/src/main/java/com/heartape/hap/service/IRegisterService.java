package com.heartape.hap.service;

import com.heartape.hap.entity.dto.CreatorDTO;

public interface IRegisterService {

    void checkEmail(String email);

    void checkPhone(String mobile);

    void register(CreatorDTO creatorDTO);
}
