package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.DiscussComment;
import com.heartape.hap.business.entity.DiscussCommentChild;
import com.heartape.hap.business.entity.Topic;
import com.heartape.hap.business.entity.TopicDiscuss;
import com.heartape.hap.business.entity.bo.LabelBO;
import com.heartape.hap.business.entity.bo.TopicBO;
import com.heartape.hap.business.entity.bo.TopicSimpleBO;
import com.heartape.hap.business.entity.dto.TopicDTO;
import com.heartape.hap.business.exception.PermissionNoRemoveException;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import com.heartape.hap.business.mapper.DiscussCommentChildMapper;
import com.heartape.hap.business.mapper.DiscussCommentMapper;
import com.heartape.hap.business.mapper.TopicDiscussMapper;
import com.heartape.hap.business.mapper.TopicMapper;
import com.heartape.hap.business.service.ITopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.business.utils.AssertUtils;
import com.heartape.hap.business.utils.StringTransformUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private TopicDiscussMapper topicDiscussMapper;

    @Autowired
    private DiscussCommentMapper discussCommentMapper;

    @Autowired
    private DiscussCommentChildMapper discussCommentChildMapper;

    @Autowired
    private StringTransformUtils stringTransformUtils;

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Autowired
    private AssertUtils assertUtils;

    @Override
    public void create(TopicDTO topicDTO) {
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicDTO, topic);
        String description = topic.getDescription();
        String ignoreBlank = stringTransformUtils.IgnoreBlank(description);
        if (ignoreBlank.length() > 100) {
            String simpleDescription = ignoreBlank.substring(0, 100);
            topic.setSimpleDescription(simpleDescription);
            topic.setIsLong(true);
        } else {
            topic.setIsLong(false);
        }
        baseMapper.insert(topic);
    }

    @Override
    public PageInfo<TopicSimpleBO> list(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Topic> list = query().select("topic_id","title","is_picture","main_picture","is_long","simple_description","description","created_time").list();
        PageInfo<Topic> pageInfo = PageInfo.of(list);
        PageInfo<TopicSimpleBO> topicBOPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, topicBOPageInfo);
        List<TopicSimpleBO> collect = list.stream().map(topic -> {
            TopicSimpleBO topicBO = new TopicSimpleBO();
            BeanUtils.copyProperties(topic, topicBO);
            return topicBO;
        }).collect(Collectors.toList());
        topicBOPageInfo.setList(collect);
        return topicBOPageInfo;
    }

    @Override
    public PageInfo<TopicSimpleBO> creatorList(Integer page, Integer size) {
        return null;
    }

    @Override
    public TopicBO detail(Long topicId) {
        Topic topic = baseMapper.selectOneLabel(topicId);
        // 转换标签
        List<LabelBO> labelBOList = topic.getLabel().stream().map(label -> {
            LabelBO labelBO = new LabelBO();
            BeanUtils.copyProperties(label, labelBO);
            return labelBO;
        }).collect(Collectors.toList());
        TopicBO topicBO = new TopicBO();
        BeanUtils.copyProperties(topic, topicBO);
        topicBO.setLabel(labelBOList);
        return topicBO;
    }

    @Override
    public void remove(Long topicId) {
        long uid = tokenFeignService.getUid();
        int delete = baseMapper.delete(new QueryWrapper<Topic>().eq("topic_id", topicId).eq("uid", uid));
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(String.format("没有删除权限,topicId:%s,uid:%s", topicId, uid)));
        // todo: 考虑是否需要rabbitmq异步删除
        topicDiscussMapper.delete(new QueryWrapper<TopicDiscuss>().eq("topic_id", topicId));
        discussCommentMapper.delete(new QueryWrapper<DiscussComment>().eq("topic_id", topicId));
        discussCommentChildMapper.delete(new QueryWrapper<DiscussCommentChild>().eq("topic_id", topicId));
    }
}
