package com.heartape.hap.entity.ro;

import lombok.Data;

@Data
public class LoginCodeRO {

    private String codeId;
    private String code;

    public LoginCodeRO(String codeId, String code) {
        this.codeId = codeId;
        this.code = code;
    }
}
