package com.heartape.hap.business;

import com.heartape.hap.business.statistics.ArticleHotStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

@SpringBootTest
public class CumulativeOperateStatisticsTest {

    @Autowired
    private ArticleHotStatistics articleHotStatistics;

    @Test
    public void createArticleHotData() {
        long source = 100L;
        for (int i = 0; i <100; i++) {
            int i1 = articleHotStatistics.operateIncrement(source + i, i);
            System.out.println(i1);
        }
    }

    @Test
    public void testArticleHot1() {
        long source = 2L;
        int i1 = articleHotStatistics.operateIncrement(source, 20);
        System.out.println(i1);
        int i2 = articleHotStatistics.operateDecrement(source, 5);
        System.out.println(i2);
        int i3 = articleHotStatistics.operateMultiple(source, 2);
        System.out.println(i3);
        int i = articleHotStatistics.operateNumber(source);
        System.out.println(i);
        int i4 = articleHotStatistics.operateNumberAll();
        System.out.println(i4);
        // boolean i3 = articleHotStatistics.removeOperate(source);
        // System.out.println(i3);
    }

    @Test
    public void testArticleHot2() {
        Set<ZSetOperations.TypedTuple<Long>> typedTuples = articleHotStatistics.operateNumberPage(2, 5);
        for (ZSetOperations.TypedTuple<Long> typedTuple : typedTuples) {
            Double score = typedTuple.getScore();
            Long value = typedTuple.getValue();
            System.out.println(value);
            System.out.println(score);
        }
    }
}
