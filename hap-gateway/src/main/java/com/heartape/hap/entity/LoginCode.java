package com.heartape.hap.entity;

import lombok.Data;

@Data
public class LoginCode {

    private String codeId;
    private String code;

    public LoginCode(String codeId, String code) {
        this.codeId = codeId;
        this.code = code;
    }
}
