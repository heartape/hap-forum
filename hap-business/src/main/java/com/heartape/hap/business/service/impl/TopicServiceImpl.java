package com.heartape.hap.business.service.impl;

import com.heartape.hap.business.entity.Topic;
import com.heartape.hap.business.entity.ro.TopicRO;
import com.heartape.hap.business.mapper.TopicMapper;
import com.heartape.hap.business.service.ITopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements ITopicService {

    @Override
    public void create(TopicRO topic) {

    }
}
