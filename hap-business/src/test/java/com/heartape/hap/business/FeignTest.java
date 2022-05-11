package com.heartape.hap.business;

import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FeignTest {

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Test
    public void nicknameTest() {
        long uid = 1518946330134302721L;
        String nickname = tokenFeignService.getNickname(uid);
        System.out.println(nickname);
    }
}
