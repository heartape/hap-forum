package com.heartape.hap.business.service;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.DiscussComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.business.entity.bo.DiscussCommentBO;
import com.heartape.hap.business.entity.dto.DiscussCommentDTO;

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

    void remove(Long commentId);
}
