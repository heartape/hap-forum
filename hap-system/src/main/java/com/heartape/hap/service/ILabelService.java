package com.heartape.hap.service;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.entity.Label;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.entity.bo.LabelBO;
import com.heartape.hap.entity.bo.SimpleLabelBO;
import com.heartape.hap.entity.ro.LabelChildRO;
import com.heartape.hap.entity.ro.LabelRO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
public interface ILabelService extends IService<Label> {

    /**
     * 查询标签简单信息
     */
    PageInfo<SimpleLabelBO> list(String name, Integer page, Integer size);

    /**
     * 查询标签详细信息
     */
    LabelBO detail(Long labelId);

    /**
     * 创建标签
     */
    void create(LabelRO label);

    void createChild(LabelChildRO labelChildRO);
}
