package com.heartape.hap;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.statistics.AbstractMasterSlaveOperateStatistics;
import com.heartape.hap.statistics.ArticleCollectionStatistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MasterSlaveOperateStatisticsTest {

    @Autowired
    private ArticleCollectionStatistics articleCollectionStatistics;

    @Test
    public void test1() {
        long masterId = 123456;
        long slaveId1 = 111111;
        long slaveId2 = 222222;
        long slaveId3 = 333333;
        long slaveId4 = 444444;
        long slaveId5 = 555555;
        long insert1 = articleCollectionStatistics.insert(masterId, slaveId1);
        System.out.println(insert1);
        long insert2 = articleCollectionStatistics.insert(masterId, slaveId2);
        System.out.println(insert2);
        long insert3 = articleCollectionStatistics.insert(masterId, slaveId3);
        System.out.println(insert3);
        long total1 = articleCollectionStatistics.total(masterId);
        System.out.println(total1);
        long insert4 = articleCollectionStatistics.insert(masterId, slaveId4);
        System.out.println(insert4);
        long insert5 = articleCollectionStatistics.insert(masterId, slaveId5);
        System.out.println(insert5);
        long total = articleCollectionStatistics.total(masterId);
        System.out.println(total);

        List<AbstractMasterSlaveOperateStatistics.Slave> select = articleCollectionStatistics.select(masterId, 1, 2);
        PageInfo<AbstractMasterSlaveOperateStatistics.Slave> pageInfo = PageInfo.of(select);
        System.out.println(pageInfo);
    }

    @Test
    public void test2() {
        long masterId = 123456;
        long slaveId5 = 555555;
        long insert = articleCollectionStatistics.insert(masterId, slaveId5);
        System.out.println(insert);
    }

    @Test
    public void test3() {
        long masterId = 123456;
        long slaveId5 = 555555;
        boolean delete = articleCollectionStatistics.delete(masterId, slaveId5);
        System.out.println(delete);
    }
}
