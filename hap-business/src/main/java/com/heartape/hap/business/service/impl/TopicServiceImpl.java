package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import com.heartape.hap.business.feign.HapUserDetails;
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
        String simpleDescription = ignoreBlank.length() > 100 ? ignoreBlank.substring(0, 100) : ignoreBlank;
        topic.setSimpleDescription(simpleDescription);

        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        Long uid = tokenInfo.getUid();
        String avatar = tokenInfo.getAvatar();
        String nickname = tokenInfo.getNickname();
        String profile = tokenInfo.getProfile();
        topic.setUid(uid);
        topic.setAvatar(avatar);
        topic.setNickname(nickname);
        topic.setProfile(profile);
        baseMapper.insert(topic);
    }

    @Override
    public PageInfo<TopicSimpleBO> list(Integer page, Integer size) {
        LambdaQueryWrapper<Topic> queryWrapper = new QueryWrapper<Topic>().lambda();
        PageHelper.startPage(page, size);
        List<Topic> list = baseMapper.selectList(queryWrapper.select(Topic::getTopicId,Topic::getTitle,Topic::getIsPicture,
                Topic::getMainPicture,Topic::getSimpleDescription,Topic::getDescription,Topic::getCreatedTime));
        PageInfo<Topic> pageInfo = PageInfo.of(list);
        PageInfo<TopicSimpleBO> topicBOPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, topicBOPageInfo);
        List<TopicSimpleBO> collect = list.stream().map(topic -> {
            TopicSimpleBO topicBO = new TopicSimpleBO();
            BeanUtils.copyProperties(topic, topicBO);
            // todo:热度
            topicBO.setHot(125235);
            return topicBO;
        }).collect(Collectors.toList());
        topicBOPageInfo.setList(collect);
        return topicBOPageInfo;
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
        // todo:移除话题点赞
        topicBO.setLike(124634);
        topicBO.setDislike(7456);
        return topicBO;
    }

    @Override
    public void remove(Long topicId) {
        long uid = tokenFeignService.getUid();

        LambdaQueryWrapper<Topic> queryWrapper = new QueryWrapper<Topic>().lambda();
        int delete = baseMapper.delete(queryWrapper.eq(Topic::getTopicId, topicId).eq(Topic::getUid, uid));
        String message = "\n没有删除权限,\ntopicId=" + topicId + ",\nuid=" + uid;
        assertUtils.businessState(delete == 1, new PermissionNoRemoveException(message));
        // todo: rabbitmq异步删除
        LambdaQueryWrapper<TopicDiscuss> topicDiscussWrapper = new QueryWrapper<TopicDiscuss>().lambda();
        LambdaQueryWrapper<DiscussComment> discussCommentWrapper = new QueryWrapper<DiscussComment>().lambda();
        LambdaQueryWrapper<DiscussCommentChild> discussCommentChildWrapper = new QueryWrapper<DiscussCommentChild>().lambda();

        topicDiscussMapper.delete(topicDiscussWrapper.eq(TopicDiscuss::getTopicId, topicId));
        discussCommentMapper.delete(discussCommentWrapper.eq(DiscussComment::getTopicId, topicId));
        discussCommentChildMapper.delete(discussCommentChildWrapper.eq(DiscussCommentChild::getTopicId, topicId));
    }
}
