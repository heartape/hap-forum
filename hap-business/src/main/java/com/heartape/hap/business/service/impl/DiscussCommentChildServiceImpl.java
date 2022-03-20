package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.DiscussComment;
import com.heartape.hap.business.entity.DiscussCommentChild;
import com.heartape.hap.business.entity.TopicDiscuss;
import com.heartape.hap.business.entity.bo.DiscussCommentChildBO;
import com.heartape.hap.business.entity.dto.DiscussCommentChildDTO;
import com.heartape.hap.business.exception.RelyDataNotExistedException;
import com.heartape.hap.business.mapper.DiscussCommentChildMapper;
import com.heartape.hap.business.mapper.DiscussCommentMapper;
import com.heartape.hap.business.mapper.TopicDiscussMapper;
import com.heartape.hap.business.service.IDiscussCommentChildService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.business.utils.AssertUtils;
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
public class DiscussCommentChildServiceImpl extends ServiceImpl<DiscussCommentChildMapper, DiscussCommentChild> implements IDiscussCommentChildService {

    @Autowired
    private DiscussCommentMapper discussCommentMapper;

    @Autowired
    private AssertUtils assertUtils;

    @Override
    public void create(DiscussCommentChildDTO discussCommentChildDTO) {
        // 验证讨论是否存在
        Long topicId = discussCommentChildDTO.getTopicId();
        Long discussId = discussCommentChildDTO.getDiscussId();
        Long parentId = discussCommentChildDTO.getParentId();
        Long count = discussCommentMapper.selectCount(new QueryWrapper<DiscussComment>().eq("topic_id", topicId).eq("discuss_id", discussId).eq("comment_id", parentId));
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(String.format("DiscussCommentChild所依赖的DiscussComment:id=%s不存在", parentId)));

        DiscussCommentChild discussCommentChild = new DiscussCommentChild();
        BeanUtils.copyProperties(discussCommentChildDTO, discussCommentChild);
        baseMapper.insert(discussCommentChild);
    }

    @Override
    public PageInfo<DiscussCommentChildBO> list(Long commentId, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<DiscussCommentChild> children = query().eq("parent_id", commentId).list();
        PageInfo<DiscussCommentChild> childPageInfo = PageInfo.of(children);
        PageInfo<DiscussCommentChildBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(childPageInfo, boPageInfo);

        List<DiscussCommentChildBO> commentChildBOS = children.stream().map(discussCommentChild -> {
            DiscussCommentChildBO discussCommentChildBO = new DiscussCommentChildBO();
            BeanUtils.copyProperties(discussCommentChild, discussCommentChildBO);
            return discussCommentChildBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(commentChildBOS);
        return boPageInfo;
    }

    @Override
    public void remove(Long commentId) {

    }
}
