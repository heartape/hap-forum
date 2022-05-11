package com.heartape.hap.business;

import com.heartape.hap.business.constant.MessageNotificationMainTypeEnum;
import com.heartape.hap.business.constant.MessageNotificationTargetTypeEnum;
import com.heartape.hap.business.mq.producer.IMessageNotificationProducer;
import com.heartape.hap.business.statistics.ArticleHotStatistics;
import com.heartape.hap.business.statistics.ArticleLikeStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TypeOperateStatisticsTest {

    @Autowired
    private ArticleLikeStatistics articleLikeStatistics;

    @Autowired
    private IMessageNotificationProducer messageNotificationProducer;

    @Test
    public void test1() {
        boolean setNegativeOperate = articleLikeStatistics.setNegativeOperate(1L, 1L);
        System.out.println(setNegativeOperate);
        boolean setNegativeOperate1 = articleLikeStatistics.setNegativeOperate(1L, 1L);
        System.out.println(setNegativeOperate1);
        boolean setPositiveOperate = articleLikeStatistics.setPositiveOperate(1L, 1L);
        System.out.println(setPositiveOperate);
        boolean setPositiveOperate1 = articleLikeStatistics.setPositiveOperate(1L, 1L);
        System.out.println(setPositiveOperate1);
    }

    @Test
    public void test2() {
        boolean getPositiveOperate = articleLikeStatistics.getPositiveOperate(1L, 1L);
        System.out.println(getPositiveOperate);
    }

    @Test
    public void test3() {
        boolean setNegativeOperate = articleLikeStatistics.setNegativeOperate(1L, 1L);
        System.out.println(setNegativeOperate);
    }

    @Test
    public void test4() {
        boolean getNegativeOperate = articleLikeStatistics.getNegativeOperate(1L, 1L);
        System.out.println(getNegativeOperate);
    }

    @Test
    public void test5() {
        String operateType = articleLikeStatistics.getOperateType(1L, 1L);
        System.out.println(operateType);
    }

    @Test
    public void test6() {
        boolean removeOperate = articleLikeStatistics.removeOperate(1L, 1L);
        System.out.println(removeOperate);
    }

    @Test
    public void test7() {
        int number = articleLikeStatistics.getPositiveOperateNumber(1L);
        System.out.println(number);
        int number1 = articleLikeStatistics.getNegativeOperateNumber(1L);
        System.out.println(number1);
    }

    @Test
    public void testLikeArticle() {
        messageNotificationProducer.likeCreate(1L, "西瓜", 710848206937784320L, MessageNotificationMainTypeEnum.ARTICLE, 710848206937784320L, MessageNotificationTargetTypeEnum.ARTICLE);
    }

}
