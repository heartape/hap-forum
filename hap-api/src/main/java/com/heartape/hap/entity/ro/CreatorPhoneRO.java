package com.heartape.hap.entity.ro;

import com.heartape.hap.annotation.NoBlank;
import com.heartape.hap.annotation.Phone;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@ApiModel(value="注册对象", description="注册对象")
public class CreatorPhoneRO {

    @ApiModelProperty(value = "手机")
    @NoBlank
    @Phone(message = "不符合规范的手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    @NoBlank
    @Length(min = 6, max = 6, message = "请输入6位验证码")
    private String code;

    @ApiModelProperty(value = "昵称")
    @NoBlank
    @Length(min = 2, max = 16, message = "请输入2-16位长度的昵称")
    private String nickname;

}
