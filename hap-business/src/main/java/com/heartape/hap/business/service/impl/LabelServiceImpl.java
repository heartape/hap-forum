package com.heartape.hap.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.Label;
import com.heartape.hap.business.entity.bo.LabelBO;
import com.heartape.hap.business.entity.bo.SimpleLabelBO;
import com.heartape.hap.business.entity.ro.LabelRO;
import com.heartape.hap.business.mapper.LabelMapper;
import com.heartape.hap.business.service.ILabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public PageInfo<SimpleLabelBO> list(String name, Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<Label> labels = query().like("name", name).select("label_id", "name").list();
        PageInfo<Label> pageInfo = PageInfo.of(labels);
        PageInfo<SimpleLabelBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, boPageInfo);
        List<SimpleLabelBO> simpleLabelBOS = labels.stream().map(label -> {
            SimpleLabelBO simpleLabelBO = new SimpleLabelBO();
            BeanUtils.copyProperties(label, simpleLabelBO);
            return simpleLabelBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(simpleLabelBOS);
        return null;
    }

    @Override
    public LabelBO detail(Long labelId) {
        // todo:改为自联结查询
        Label label = query().eq("label_id", labelId).one();
        LabelBO labelBO = new LabelBO();
        BeanUtils.copyProperties(label, labelBO);
        return labelBO;
    }

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
