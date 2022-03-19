package com.heartape.hap.business.service;

import com.heartape.hap.business.entity.Label;
import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.business.entity.ro.LabelRO;

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
     * 创建标签
     */
    void create(LabelRO label);
}
