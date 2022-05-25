package com.heartape.hap.business;

import com.heartape.hap.business.statistics.ArticleCommentHotStatistics;
import com.heartape.hap.business.statistics.ArticleHotStatistics;
import com.heartape.hap.business.statistics.CumulativeOperateStatistics;
import com.heartape.hap.business.statistics.TopCumulativeOperateStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CumulativeOperateStatisticsTest {

    @Autowired
    private ArticleHotStatistics articleHotStatistics;

    @Autowired
    private ArticleCommentHotStatistics articleCommentHotStatistics;

    @Test
    public void createArticleHotData() {
        long source = 100L;
        for (int i = 1; i <= 100; i++) {
            int i1 = articleHotStatistics.updateIncrement(source + i, i);
            System.out.println(i1);
        }
    }

    @Test
    public void testArticleHot1() {
        long source = 2L;
        int i1 = articleHotStatistics.updateIncrement(source, 20);
        System.out.println(i1);
        int i2 = articleHotStatistics.updateDecrement(source, 5);
        System.out.println(i2);
        int i3 = articleHotStatistics.updateMultiple(source, 2);
        System.out.println(i3);
        int i = articleHotStatistics.count(source);
        System.out.println(i);
        // boolean i3 = articleHotStatistics.removeOperate(source);
        // System.out.println(i3);
    }

    @Test
    public void testArticleHot2() {
        List<TopCumulativeOperateStatistics.CumulativeValue> cumulativeValues = articleHotStatistics.selectPage(2, 5);
        for (TopCumulativeOperateStatistics.CumulativeValue  cumulativeValue : cumulativeValues) {
            Integer operate = cumulativeValue.getOperate();
            Long resourceId = cumulativeValue.getResourceId();
            System.out.println(resourceId);
            System.out.println(operate);
        }
    }

    @Test
    public void testArticleCommentHot() {
        long mainId = 111L;
        long targetId = 222L;
        int increment1 = articleCommentHotStatistics.updateIncrement(mainId, targetId, 10);
        System.out.println(increment1);
        int increment2 = articleCommentHotStatistics.updateIncrement(mainId, targetId, -5);
        System.out.println(increment2);
        int multiple = articleCommentHotStatistics.updateMultiple(mainId, targetId, 2);
        System.out.println(multiple);
        int count = articleCommentHotStatistics.count(mainId, targetId);
        System.out.println(count);
        List<CumulativeOperateStatistics.CumulativeValue> cumulativeValues = articleCommentHotStatistics.selectPage(mainId, 1, 2);
        System.out.println(cumulativeValues);
    }
}
