package com.heartape.hap.business.service;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.DiscussCommentChild;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.business.entity.bo.DiscussCommentChildBO;
import com.heartape.hap.business.entity.dto.DiscussCommentChildDTO;

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

    PageInfo<DiscussCommentChildBO> list(Long commentId, Integer page, Integer size);

    void remove(Long commentId);
}
