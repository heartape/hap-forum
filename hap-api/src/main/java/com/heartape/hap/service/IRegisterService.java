package com.heartape.hap.service;

import com.heartape.hap.entity.dto.CreatorPhoneDTO;
import com.heartape.hap.entity.dto.CreatorEmailDTO;

public interface IRegisterService {

    void checkEmail(String email);

    void checkPhone(String mobile);

    void registerEmail(CreatorEmailDTO creatorDTO);

    void registerPhone(CreatorPhoneDTO creatorPhoneDTO);
}
