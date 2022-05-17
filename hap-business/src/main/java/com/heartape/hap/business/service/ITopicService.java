package com.heartape.hap.business.service;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.Topic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.business.entity.bo.TopicBO;
import com.heartape.hap.business.entity.bo.TopicSimpleBO;
import com.heartape.hap.business.entity.dto.TopicDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
public interface ITopicService extends IService<Topic> {

    /**
     * 创建话题
     */
    void create(TopicDTO topicDTO);

    /**
     * 关注话题
     */
    boolean follow(Long topicId);

    /**
     * 查询列表
     */
    PageInfo<TopicSimpleBO> list(Integer page, Integer size);

    /**
     * 详细内容
     */
    TopicBO detail(Long topicId);

    /**
     * 删除
     */
    void remove(Long topicId);

}
