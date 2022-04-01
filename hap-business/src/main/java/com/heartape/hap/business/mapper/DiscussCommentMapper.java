package com.heartape.hap.business.mapper;

import com.heartape.hap.business.entity.DiscussComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
public interface DiscussCommentMapper extends BaseMapper<DiscussComment> {

    /**
     * 查询父评论列表并返回子评论总数
     */
    List<DiscussComment> selectWithChildCount(@Param("discuss_id") Long discussId);
}
