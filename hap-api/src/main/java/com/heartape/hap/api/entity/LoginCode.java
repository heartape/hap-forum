package com.heartape.hap.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class LoginCode {

    private String codeId;
    @JsonIgnore
    private String code;
    private String codePicture;

    public LoginCode() {
    }

    public LoginCode(String codeId, String codePicture) {
        this.codeId = codeId;
        this.codePicture = codePicture;
    }
}
