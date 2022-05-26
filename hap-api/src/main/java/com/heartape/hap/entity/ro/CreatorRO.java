package com.heartape.hap.entity.ro;

import com.heartape.hap.annotation.Phone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@Data
@ApiModel(value="注册对象", description="注册对象")
public class CreatorRO {

    @ApiModelProperty(value = "邮箱")
    @Length(min = 8, max = 16, message = "请输入8-16位长度的密码")
    @Email(message = "不符合规范的邮箱", regexp = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$")
    private String email;

    @ApiModelProperty(value = "手机")
    @Phone(message = "不符合规范的手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    @Length(min = 8, max = 16, message = "请输入8-16位长度的密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    @Length(min = 2, max = 16, message = "请输入2-16位长度的昵称")
    private String nickname;

    @ApiModelProperty(value = "角色")
    @Length(min = 5, max = 10, message = "请输入正确的角色")
    private String role;
}
