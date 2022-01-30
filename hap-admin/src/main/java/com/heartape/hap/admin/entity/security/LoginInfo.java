package com.heartape.hap.admin.entity.security;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@ApiModel(value="登录对象", description="")
public class LoginInfo {

    @ApiModelProperty(value = "账户")
    @Length(min = 8, max = 16, message = "请输入8-16位长度的用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    @Length(min = 8, max = 16, message = "请输入8-16位长度的密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "唯一标识")
    private String uuid;
}
