package com.heartape.hap.business.mapper;

import com.heartape.hap.business.entity.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
public interface TopicMapper extends BaseMapper<Topic> {

    /**
     * 查询带标签的话题信息
     */
    Topic selectOneLabel(@Param("topic_id") Long topicId);
}
