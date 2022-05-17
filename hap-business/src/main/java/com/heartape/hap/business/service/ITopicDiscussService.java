package com.heartape.hap.business.service;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.TopicDiscuss;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.business.entity.bo.TopicDiscussBO;
import com.heartape.hap.business.entity.dto.TopicDiscussDTO;

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
    boolean like(Long discussId);

    /**
     * 点踩
     */
    boolean dislike(Long discussId);

    void remove(Long discussId);
}
