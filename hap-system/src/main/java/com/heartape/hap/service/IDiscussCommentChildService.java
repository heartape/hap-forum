package com.heartape.hap.service;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.entity.DiscussCommentChild;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.entity.bo.DiscussCommentChildBO;
import com.heartape.hap.entity.dto.DiscussCommentChildDTO;
import com.heartape.hap.statistics.AbstractTypeOperateStatistics;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
public interface IDiscussCommentChildService extends IService<DiscussCommentChild> {

    void create(DiscussCommentChildDTO discussCommentChildDTO);

    void createToChild(DiscussCommentChildDTO discussCommentChildDTO);

    PageInfo<DiscussCommentChildBO> list(Long commentId, Integer page, Integer size);

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
