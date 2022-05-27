package com.heartape.hap.statistics;

import com.heartape.hap.constant.ResourceRedisKeyConstant;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 标签关注功能在推荐的时候可以作为推荐的因素，但考虑到很少有用户操作，影响很小，所以改为用户浏览或发布时记录文章的标签
 */
@Component
public class LabelBrowseStatistics extends AbstractCumulativeOperateStatistics {

    @Override
    public String getKey(long masterId) {
        return ResourceRedisKeyConstant.BROWSE_LABEL + masterId;
    }

    /**
     * todo:文章话题模块记录用户的标签偏好
     * @param labelList 标签id列表
     * @param uid 用户id
     */
    public void insert(List<Long> labelList, long uid) {
        for (Long labelId : labelList) {
            updateIncrement(uid, labelId, 1);
        }
    }
}
