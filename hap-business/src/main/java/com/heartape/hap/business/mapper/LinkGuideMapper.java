package com.heartape.hap.business.mapper;

import com.heartape.hap.business.entity.LinkGuide;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@Mapper
public interface LinkGuideMapper extends BaseMapper<LinkGuide> {

    void insert();

}
