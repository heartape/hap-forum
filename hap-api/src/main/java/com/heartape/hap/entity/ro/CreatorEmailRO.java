package com.heartape.hap.entity.ro;

import com.heartape.hap.annotation.NoBlank;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@ApiModel(value="注册对象", description="注册对象")
public class CreatorEmailRO {

    @ApiModelProperty(value = "邮箱")
    @NoBlank
    @Length(min = 6, max = 32, message = "请输入4-32位长度的邮箱")
    @Email(message = "不符合规范的邮箱", regexp = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$")
    private String email;

    @ApiModelProperty(value = "密码")
    @NoBlank
    @Length(min = 6, max = 6, message = "请输入6位验证码")
    private String code;

    @ApiModelProperty(value = "密码")
    @NoBlank
    @Length(min = 8, max = 16, message = "请输入8-16位长度的密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    @NoBlank
    @Length(min = 2, max = 16, message = "请输入2-16位长度的昵称")
    private String nickname;

}
