package com.heartape.hap.mapper;

import com.heartape.hap.entity.Topic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@Mapper
public interface TopicMapper extends BaseMapper<Topic> {

    /**
     * 查询带标签的话题信息
     */
    Topic selectOneLabel(@Param("topic_id") Long topicId);
}
