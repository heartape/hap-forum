package com.heartape.hap.business.mapper;

import com.heartape.hap.business.entity.TopicDiscuss;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
public interface TopicDiscussMapper extends BaseMapper<TopicDiscuss> {

    /**
     * 查询讨论列表并返回评论总数
     */
    List<TopicDiscuss> selectWithCommentCount(@Param("topic_id") Long topicId);
}
