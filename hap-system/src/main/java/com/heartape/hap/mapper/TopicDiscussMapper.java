package com.heartape.hap.mapper;

import com.heartape.hap.entity.TopicDiscuss;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
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
@Mapper
public interface TopicDiscussMapper extends BaseMapper<TopicDiscuss> {

    /**
     * 查询讨论列表并返回评论总数
     */
    List<TopicDiscuss> selectWithCommentCount(@Param("topic_id") Long topicId);
}
