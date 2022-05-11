package com.heartape.hap.business;

import com.heartape.hap.business.statistics.ArticleHotStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CumulativeOperateStatisticsTest {

    @Autowired
    private ArticleHotStatistics articleHotStatistics;

    @Test
    public void testArticleHot() {
        long source = 1L;
        int i1 = articleHotStatistics.operateIncrement(source, 2);
        System.out.println(i1);
        int i2 = articleHotStatistics.operateDecrement(source, 1);
        System.out.println(i2);
        int i = articleHotStatistics.operateNumber(source);
        System.out.println(i);
        boolean i3 = articleHotStatistics.removeOperate(source);
        System.out.println(i3);
    }

    @Test
    public void testArticleHotMultiple() {
        long source = 1L;
        int i1 = articleHotStatistics.operateIncrement(source, 100);
        System.out.println(i1);
        int i2 = articleHotStatistics.operateMultipleIncrement(source, 2);
        System.out.println(i2);
        boolean i3 = articleHotStatistics.removeOperate(source);
        System.out.println(i3);
        int i = articleHotStatistics.operateNumber(source);
        System.out.println(i);
    }
}
