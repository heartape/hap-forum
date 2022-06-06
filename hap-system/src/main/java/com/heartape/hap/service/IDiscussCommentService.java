package com.heartape.hap.service;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.entity.DiscussComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.entity.bo.DiscussCommentBO;
import com.heartape.hap.entity.dto.DiscussCommentDTO;
import com.heartape.hap.statistics.AbstractTypeOperateStatistics;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
public interface IDiscussCommentService extends IService<DiscussComment> {

    void create(DiscussCommentDTO discussCommentDTO);

    PageInfo<DiscussCommentBO> list(Long discussId, Integer page, Integer size);

    /**
     * 点赞
     */
    AbstractTypeOperateStatistics.TypeNumber like(Long commentId);

    /**
     * 点踩
     */
    AbstractTypeOperateStatistics.TypeNumber dislike(Long commentId);

    void remove(Long commentId);

}
