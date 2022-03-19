package com.heartape.hap.business.service.impl;

import com.heartape.hap.business.entity.Label;
import com.heartape.hap.business.entity.ro.LabelRO;
import com.heartape.hap.business.mapper.LabelMapper;
import com.heartape.hap.business.service.ILabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements ILabelService {

    @Override
    public void create(LabelRO labelRO) {
        // todo:检查parentId
        Label label = new Label();
        BeanUtils.copyProperties(labelRO, label);
        String introduce = labelRO.getIntroduce();
        String simpleIntroduce;
        boolean isLong;
        if (introduce.length() > 100) {
            simpleIntroduce = introduce.substring(0, 100);
            isLong = true;
        } else {
            simpleIntroduce = introduce;
            isLong = false;
        }
        label.setSimpleIntroduce(simpleIntroduce);
        label.setIsLong(isLong);
        this.baseMapper.insert(label);
    }
}
