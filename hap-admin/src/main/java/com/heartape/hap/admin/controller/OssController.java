package com.heartape.hap.admin.controller;

import com.aliyuncs.exceptions.ClientException;
import com.heartape.hap.admin.utils.OssUtils;
import com.heartape.hap.admin.response.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

/**
 * @author heartape
 * @description 对象存储
 * @since 2021/12/2
 */
@RestController
@RequestMapping("/oss")
@Slf4j
@Api(tags = "oss图片上传与授权")
@Validated
public class OssController {

    @Autowired
    private OssUtils ossUtils;

    @GetMapping("/autograph")
    public Result pictureAutograph() {
        return Result.success(ossUtils.uploadAutographKey(OssUtils.PICTURE_DIR));
    }

    /**
     * 返回临时url,并且可对用户上传行为进行监控
     */
    @GetMapping("/picture/url")
    public Result pictureUrl(@NotEmpty String fileName) throws ClientException {
        return Result.success(ossUtils.proxyPictureUrl(ossUtils.pictureTemporaryInnerUrl(ossUtils.getBucket(),fileName)));
    }

}
