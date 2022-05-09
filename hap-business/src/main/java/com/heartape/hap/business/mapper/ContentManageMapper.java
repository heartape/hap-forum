package com.heartape.hap.business.mapper;

import com.heartape.hap.business.entity.ContentManage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ContentManageMapper {

    /**
     * 讨论内容
     * @param uid 用户id
     * @param startTime 起始时间
     * @param endTime 结束时间
     */
    List<ContentManage> selectDiscussList(@Param("uid") long uid, @Param("startTime") LocalDate startTime, @Param("endTime") LocalDate endTime);
}
