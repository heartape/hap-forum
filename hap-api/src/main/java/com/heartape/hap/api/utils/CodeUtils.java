package com.heartape.hap.api.utils;

import com.heartape.hap.api.config.CaptchaServiceSingleton;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class CodeUtils {

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * todo:创建验证图片
     */
    public String createPicture(String codeId) throws IOException {
        // the output stream to render the captcha image as jpeg into
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();

        // call the ImageCaptchaService getChallenge method
        BufferedImage challenge = CaptchaServiceSingleton.getInstance().getImageChallengeForID(codeId, tokenUtils.getRequest().getLocale());

        // a jpeg encoder
        JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
        jpegEncoder.encode(challenge);
        byte[] bytes = jpegOutputStream.toByteArray();
        // base64格式前加上 data:image/jpg;base64,
        return new BASE64Encoder().encode(bytes);
    }

}
