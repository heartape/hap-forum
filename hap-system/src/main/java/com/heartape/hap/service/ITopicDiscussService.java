package com.heartape.hap.service;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.entity.TopicDiscuss;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.entity.bo.TopicDiscussBO;
import com.heartape.hap.entity.dto.TopicDiscussDTO;
import com.heartape.hap.statistics.AbstractTypeOperateStatistics;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
public interface ITopicDiscussService extends IService<TopicDiscuss> {

    void create(TopicDiscussDTO topicDiscuss);

    PageInfo<TopicDiscussBO> list(Long topicId, Integer page, Integer size);

    /**
     * 点赞
     */
    AbstractTypeOperateStatistics.TypeNumber like(Long discussId);

    /**
     * 点踩
     */
    AbstractTypeOperateStatistics.TypeNumber dislike(Long discussId);

    void remove(Long discussId);
}
