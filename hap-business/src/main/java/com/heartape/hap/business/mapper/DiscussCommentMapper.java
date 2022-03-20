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
     * 查询带子评论的评论树
     */
    List<DiscussComment> selectTreeList(@Param("discuss_id") Long discussId, @Param("page") Integer page, @Param("size") Integer size);
}
