package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.DiscussComment;
import com.heartape.hap.business.entity.TopicDiscuss;
import com.heartape.hap.business.entity.bo.DiscussCommentBO;
import com.heartape.hap.business.entity.bo.DiscussCommentChildBO;
import com.heartape.hap.business.entity.dto.DiscussCommentDTO;
import com.heartape.hap.business.exception.RelyDataNotExistedException;
import com.heartape.hap.business.mapper.DiscussCommentMapper;
import com.heartape.hap.business.mapper.TopicDiscussMapper;
import com.heartape.hap.business.service.IDiscussCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.business.utils.AssertUtils;
import com.heartape.hap.business.utils.SqlUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class DiscussCommentServiceImpl extends ServiceImpl<DiscussCommentMapper, DiscussComment> implements IDiscussCommentService {

    @Autowired
    private TopicDiscussMapper topicDiscussMapper;

    @Autowired
    private AssertUtils assertUtils;

    @Autowired
    private SqlUtils sqlUtils;

    @Override
    public void create(DiscussCommentDTO discussCommentDTO) {
        // 验证讨论是否存在
        Long topicId = discussCommentDTO.getTopicId();
        Long discussId = discussCommentDTO.getDiscussId();
        Long count = topicDiscussMapper.selectCount(new QueryWrapper<TopicDiscuss>().eq("topic_id", topicId).eq("discuss_id", discussId));
        assertUtils.businessState(count == 1, new RelyDataNotExistedException(String.format("TopicDiscuss所依赖的Topic:id=%s不存在", topicId)));

        DiscussComment discussComment = new DiscussComment();
        BeanUtils.copyProperties(discussCommentDTO, discussComment);
        baseMapper.insert(discussComment);
    }

    @Override
    public PageInfo<DiscussCommentBO> list(Long discussId, Integer page, Integer size) {
        // 检查分页是否超出范围
        Long count = baseMapper.selectCount(new QueryWrapper<DiscussComment>().eq("discuss_id", discussId));
        List<DiscussComment> comments = new ArrayList<>();
        if (count.intValue() > (page-1)*size) {
            comments = baseMapper.selectTreeList(discussId, page, size);
        }
        List<DiscussCommentBO> discussCommentBOS = comments.stream().map(discussComment -> {
            DiscussCommentBO discussCommentBO = new DiscussCommentBO();
            BeanUtils.copyProperties(discussComment, discussCommentBO);
            // 转换child
            List<DiscussCommentChildBO> discussCommentChildBOS = discussComment.getChildren().stream().map(discussCommentChild -> {
                DiscussCommentChildBO discussCommentChildBO = new DiscussCommentChildBO();
                BeanUtils.copyProperties(discussCommentChild, discussCommentChildBO);
                return discussCommentChildBO;
            }).collect(Collectors.toList());
            discussCommentBO.setChildren(discussCommentChildBOS);
            return discussCommentBO;
        }).collect(Collectors.toList());
        return sqlUtils.assemblePageInfo(discussCommentBOS, count, page, size);
    }

    @Override
    public void remove(Long commentId) {

    }
}
