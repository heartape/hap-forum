package com.heartape.hap.service;

import com.heartape.hap.entity.LinkGuide;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.entity.bo.LinkGuideBO;

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
     * 热点导航
     */
    List<LinkGuideBO> showHot();
    /**
     * 查询链接导航列表
     */
    List<LinkGuideBO> showList(Integer page, Integer size);

    void create();

}
