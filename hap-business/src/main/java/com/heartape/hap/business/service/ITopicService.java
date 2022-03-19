package com.heartape.hap.business.service;

import com.heartape.hap.business.entity.Topic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.business.entity.ro.TopicRO;

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
    void create(TopicRO topic);
}
