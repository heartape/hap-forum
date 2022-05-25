package com.heartape.hap.business.statistics;

import com.heartape.hap.business.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

@Component
public class DiscussCollectionStatistics extends AbstractMasterSlaveOperateStatistics {

    @Override
    public String masterKey(long masterId) {
        return ResourceRedisKeyConstant.COLLECTION_DISCUSS + masterId;
    }

    @Override
    public String slaveKey(long slaveId) {
        return ResourceRedisKeyConstant.COLLECTION_DISCUSS + ResourceRedisKeyConstant.SLAVE + slaveId;
    }
}
