package com.heartape.hap.statistics;

import com.heartape.hap.constant.ResourceStatisticsKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class ArticleCollectionStatistics extends AbstractMasterSlaveOperateStatistics {

    @Override
    public String masterKey(long masterId) {
        return ResourceStatisticsKeyConstant.COLLECTION_ARTICLE + masterId;
    }

    @Override
    public String slaveKey(long slaveId) {
        return ResourceStatisticsKeyConstant.COLLECTION_ARTICLE + ResourceStatisticsKeyConstant.SLAVE + slaveId;
    }
}
