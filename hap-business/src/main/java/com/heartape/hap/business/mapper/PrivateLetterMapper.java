package com.heartape.hap.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heartape.hap.business.entity.PrivateLetter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PrivateLetterMapper extends BaseMapper<PrivateLetter> {

    /**
     * 查询当前用户的私信名单
     * @param targetUid 用户id
     * @param blacklist 黑名单
     */
    List<PrivateLetter> selectCreatorList(@Param("target_uid") Long targetUid, @Param("blacklist") List<Long> blacklist);
}
