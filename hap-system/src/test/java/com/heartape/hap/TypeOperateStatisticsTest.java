package com.heartape.hap;

import com.heartape.hap.statistics.AbstractTypeOperateStatistics;
import com.heartape.hap.statistics.ArticleLikeStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TypeOperateStatisticsTest {

    @Autowired
    private ArticleLikeStatistics articleLikeStatistics;

    @Test
    public void test1() {
        long resourceId = 11111L;
        long sponsorId = 22222L;
        AbstractTypeOperateStatistics.TypeNumber like1 = articleLikeStatistics.insert(sponsorId, resourceId, AbstractTypeOperateStatistics.TypeEnum.POSITIVE);
        System.out.println(like1);
        AbstractTypeOperateStatistics.TypeNumber like2 = articleLikeStatistics.insert(sponsorId, resourceId, AbstractTypeOperateStatistics.TypeEnum.POSITIVE);
        System.out.println(like2);
        AbstractTypeOperateStatistics.TypeNumber dislike1 = articleLikeStatistics.insert(sponsorId, resourceId, AbstractTypeOperateStatistics.TypeEnum.NEGATIVE);
        System.out.println(dislike1);
        AbstractTypeOperateStatistics.TypeNumber dislike2 = articleLikeStatistics.insert(sponsorId, resourceId, AbstractTypeOperateStatistics.TypeEnum.NEGATIVE);
        System.out.println(dislike2);
        AbstractTypeOperateStatistics.TypeNumber like3 = articleLikeStatistics.insert(sponsorId, resourceId, AbstractTypeOperateStatistics.TypeEnum.POSITIVE);
        System.out.println(like3);
        AbstractTypeOperateStatistics.TypeNumber dislike3 = articleLikeStatistics.insert(sponsorId, resourceId, AbstractTypeOperateStatistics.TypeEnum.NEGATIVE);
        System.out.println(dislike3);
        AbstractTypeOperateStatistics.TypeNumber like4 = articleLikeStatistics.insert(sponsorId, resourceId, AbstractTypeOperateStatistics.TypeEnum.POSITIVE);
        System.out.println(like4);
    }

    @Test
    public void test2() {
        long targetId = 11111L;
        long sponsorId = 22222L;
        int positiveNumber = articleLikeStatistics.selectPositiveNumber(targetId);
        System.out.println(positiveNumber);
        int negativeNumber = articleLikeStatistics.selectNegativeNumber(targetId);
        System.out.println(negativeNumber);
        AbstractTypeOperateStatistics.TypeEnum type = articleLikeStatistics.type(sponsorId, targetId);
        System.out.println(type);
    }

}
