package com.heartape.hap.business.mapper;

import com.heartape.hap.business.entity.CommentManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CommentManageMapper {

    /**
     * 文章评论
     * @param uid 用户id
     * @param startTime 起始时间
     * @param endTime 结束时间
     */
    List<CommentManage> selectArticleList(@Param("uid") long uid, @Param("startTime") LocalDate startTime, @Param("endTime") LocalDate endTime);

    /**
     * 讨论评论
     * @param uid 用户id
     * @param startTime 起始时间
     * @param endTime 结束时间
     */
    List<CommentManage> selectDiscussList(@Param("uid") long uid, @Param("startTime") LocalDate startTime, @Param("endTime") LocalDate endTime);
}
