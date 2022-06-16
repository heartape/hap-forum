package com.heartape.hap;

import com.heartape.hap.entity.bo.LabelBO;
import com.heartape.hap.service.ILabelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LabelTest {

    @Autowired
    private ILabelService labelService;

    @Test
    public void detailTest() {
        LabelBO detail = labelService.detail(710975274044358657L);
        System.out.println(detail);
    }

}
