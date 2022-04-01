package com.heartape.hap.business.entity.ro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@ApiModel(value="注册对象", description="注册对象")
public class RegisterInfo {

    @ApiModelProperty(value = "账户")
    @Length(min = 8, max = 16, message = "请输入8-16位长度的用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    @Length(min = 8, max = 16, message = "请输入8-16位长度的密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    @Length(min = 2, max = 16, message = "请输入2-16位长度的昵称")
    private String nickname;

    @ApiModelProperty(value = "角色")
    @Length(min = 5, max = 10, message = "请输入正确的角色")
    private String role;

    @ApiModelProperty(value = "手机")
    private String mobile;

    @ApiModelProperty(value = "验证码")
    private String code;
}
