package com.heartape.hap.business.service;

import com.heartape.hap.business.entity.LinkGuide;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
public interface ILinkGuideService extends IService<LinkGuide> {

    /**
     * 查询链接导航列表
     */
    List<LinkGuide> showList(Integer page, Integer size);

    void create();
}
