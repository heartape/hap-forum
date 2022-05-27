package com.heartape.hap;

import com.heartape.hap.statistics.AbstractPairOperateStatistics;
import com.heartape.hap.statistics.CreatorFollowStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PairOperateStatisticsTest {

    @Autowired
    private CreatorFollowStatistics creatorFollowStatistics;

    @Test
    public void testCreatorFollow() {
        long sponsorId1 = 111111L;
        long sponsorId2 = 222222L;
        long targetId1 = 111111L;
        long targetId2 = 222222L;
        long targetId3 = 333333L;
        long targetId4 = 444444L;
        long targetId5 = 555555L;
        long targetId6 = 666666L;

        int insert1 = creatorFollowStatistics.insert(sponsorId1, targetId1);
        System.out.println(insert1);
        int insert2 = creatorFollowStatistics.insert(sponsorId1, targetId2);
        System.out.println(insert2);
        int insert3 = creatorFollowStatistics.insert(sponsorId1, targetId3);
        System.out.println(insert3);
        int insert4 = creatorFollowStatistics.insert(sponsorId1, targetId4);
        System.out.println(insert4);
        int insert5 = creatorFollowStatistics.insert(sponsorId1, targetId5);
        System.out.println(insert5);
        int insert6 = creatorFollowStatistics.insert(sponsorId1, targetId6);
        System.out.println(insert6);
        int insert11 = creatorFollowStatistics.insert(sponsorId2, targetId1);
        System.out.println(insert11);
        int insert12 = creatorFollowStatistics.insert(sponsorId2, targetId2);
        System.out.println(insert12);
        int insert13 = creatorFollowStatistics.insert(sponsorId2, targetId3);
        System.out.println(insert13);
        int insert14 = creatorFollowStatistics.insert(sponsorId2, targetId4);
        System.out.println(insert14);
        int insert15 = creatorFollowStatistics.insert(sponsorId2, targetId5);
        System.out.println(insert15);
        int insert16 = creatorFollowStatistics.insert(sponsorId2, targetId6);
        System.out.println(insert16);
    }

    @Test
    public void testCreatorFollow2() {
        long sponsorId1 = 111111L;
        long sponsorId2 = 222222L;
        long targetId1 = 111111L;
        long targetId2 = 222222L;
        long sponsor1 = creatorFollowStatistics.selectSponsor(sponsorId1);
        System.out.println(sponsor1);
        long sponsor2 = creatorFollowStatistics.selectSponsor(sponsorId2);
        System.out.println(sponsor2);
        long target1 = creatorFollowStatistics.selectTarget(targetId1);
        System.out.println(target1);
        long target2 = creatorFollowStatistics.selectTarget(targetId2);
        System.out.println(target2);
        List<AbstractPairOperateStatistics.Target> targets = creatorFollowStatistics.page(sponsorId1, 1, 2);
        System.out.println(targets);
    }
}
