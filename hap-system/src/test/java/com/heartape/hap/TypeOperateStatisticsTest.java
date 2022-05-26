package com.heartape.hap;

import com.heartape.hap.statistics.ArticleLikeStatistics;
import com.heartape.hap.statistics.TypeOperateStatistics;
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
        TypeOperateStatistics.TypeNumber like1 = articleLikeStatistics.insert(sponsorId, resourceId, TypeOperateStatistics.TypeEnum.POSITIVE);
        System.out.println(like1);
        TypeOperateStatistics.TypeNumber like2 = articleLikeStatistics.insert(sponsorId, resourceId, TypeOperateStatistics.TypeEnum.POSITIVE);
        System.out.println(like2);
        TypeOperateStatistics.TypeNumber dislike1 = articleLikeStatistics.insert(sponsorId, resourceId, TypeOperateStatistics.TypeEnum.NEGATIVE);
        System.out.println(dislike1);
        TypeOperateStatistics.TypeNumber dislike2 = articleLikeStatistics.insert(sponsorId, resourceId, TypeOperateStatistics.TypeEnum.NEGATIVE);
        System.out.println(dislike2);
        TypeOperateStatistics.TypeNumber like3 = articleLikeStatistics.insert(sponsorId, resourceId, TypeOperateStatistics.TypeEnum.POSITIVE);
        System.out.println(like3);
        TypeOperateStatistics.TypeNumber dislike3 = articleLikeStatistics.insert(sponsorId, resourceId, TypeOperateStatistics.TypeEnum.NEGATIVE);
        System.out.println(dislike3);
        TypeOperateStatistics.TypeNumber like4 = articleLikeStatistics.insert(sponsorId, resourceId, TypeOperateStatistics.TypeEnum.POSITIVE);
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
        TypeOperateStatistics.TypeEnum type = articleLikeStatistics.type(sponsorId, targetId);
        System.out.println(type);
    }

}
