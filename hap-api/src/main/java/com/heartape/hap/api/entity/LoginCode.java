package com.heartape.hap.api.entity;

import lombok.Data;

@Data
public class LoginCode {

    private String codeId;
    private String codePicture;

    public LoginCode() {
    }

    public LoginCode(String codeId, String codePicture) {
        this.codeId = codeId;
        this.codePicture = codePicture;
    }
}
