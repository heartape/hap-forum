package com.heartape.hap.business;

import com.heartape.hap.business.statistics.LabelFollowStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.ZoneId;

@SpringBootTest
public class PairOperateStatisticsTest {

    @Autowired
    private LabelFollowStatistics labelFollowStatistics;

    @Test
    public void test1() {
        long source = 324L;
        long uid = 324L;

        LocalDateTime now1 = LocalDateTime.now();
        long epochMilli1 = now1.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        boolean setOperate = labelFollowStatistics.setOperate(source, uid, epochMilli1);
        System.out.println(setOperate);
        LocalDateTime now2 = LocalDateTime.now();
        long epochMilli2 = now2.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        boolean setOperate1 = labelFollowStatistics.setOperate(source, uid, epochMilli2);
        System.out.println(setOperate1);
        long sourceOperate = labelFollowStatistics.getSourceOperate(source, uid);
        System.out.println(sourceOperate);
        long peopleOperate = labelFollowStatistics.getPeopleOperate(source, uid);
        System.out.println(peopleOperate);
        LocalDateTime now3 = LocalDateTime.now();
        long epochMilli3 = now3.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        boolean setOperate2 = labelFollowStatistics.setOperate(source, uid, epochMilli3);
        System.out.println(setOperate2);
        long sourceOperate1 = labelFollowStatistics.getSourceOperate(source, uid);
        System.out.println(sourceOperate1);
        long peopleOperate1 = labelFollowStatistics.getPeopleOperate(source, uid);
        System.out.println(peopleOperate1);
    }

    @Test
    public void test2() {
        long source = 324L;
        long uid = 324L;
        LocalDateTime now = LocalDateTime.now();
        long epochMilli = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        boolean setOperate = labelFollowStatistics.setOperate(source, uid, epochMilli);
        System.out.println(setOperate);
        long sourceOperate = labelFollowStatistics.getSourceOperate(source, uid);
        System.out.println(sourceOperate);
        long peopleOperate = labelFollowStatistics.getPeopleOperate(source, uid);
        System.out.println(peopleOperate);
    }

}
