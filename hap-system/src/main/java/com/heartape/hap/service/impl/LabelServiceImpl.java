package com.heartape.hap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.entity.Label;
import com.heartape.hap.entity.bo.LabelBO;
import com.heartape.hap.entity.bo.LabelSimpleBO;
import com.heartape.hap.entity.bo.SimpleLabelBO;
import com.heartape.hap.entity.ro.LabelChildRO;
import com.heartape.hap.entity.ro.LabelRO;
import com.heartape.hap.exception.RelyDataNotExistedException;
import com.heartape.hap.exception.ResourceNotExistedException;
import com.heartape.hap.mapper.LabelMapper;
import com.heartape.hap.service.ILabelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.utils.AssertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
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

    @Autowired
    private AssertUtils assertUtils;

    @Override
    public PageInfo<SimpleLabelBO> list(String name, Integer page, Integer size) {
        LambdaQueryWrapper<Label> queryWrapper = new QueryWrapper<Label>().lambda();
        if (StringUtils.hasText(name)) {
            queryWrapper.likeRight(Label::getName, name);
        }
        PageHelper.startPage(page, size);
        List<Label> labels = baseMapper.selectList(queryWrapper.select(Label::getLabelId, Label::getName));
        PageInfo<Label> pageInfo = PageInfo.of(labels);
        PageInfo<SimpleLabelBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, boPageInfo);
        List<SimpleLabelBO> simpleLabelBOS = labels.stream().map(label -> {
            SimpleLabelBO simpleLabelBO = new SimpleLabelBO();
            BeanUtils.copyProperties(label, simpleLabelBO);
            return simpleLabelBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(simpleLabelBOS);
        return boPageInfo;
    }

    @Override
    public LabelBO detail(Long labelId) {
        LambdaQueryWrapper<Label> queryWrapper = new QueryWrapper<Label>().lambda();
        queryWrapper.eq(Label::getLabelId, labelId).or().eq(Label::getParentId, labelId);
        List<Label> labels = baseMapper.selectList(queryWrapper);
        // 有一级标签则查询的一定为一级标签
        Optional<Label> parentLabelOptional = labels.stream().filter(label -> label.getLevel().equals(1)).findFirst();
        if (parentLabelOptional.isPresent()) {
            Label label = parentLabelOptional.get();
            LabelBO labelBO = new LabelBO();
            BeanUtils.copyProperties(label, labelBO);
            // 子标签只需要简要信息
            List<LabelSimpleBO> labelChildren = labels.stream().filter(label1 -> !label1.getLevel().equals(1)).map(label1 -> {
                LabelSimpleBO labelSimpleBO = new LabelSimpleBO();
                labelSimpleBO.setLabelId(label1.getLabelId());
                labelSimpleBO.setName(label1.getName());
                return labelSimpleBO;
            }).collect(Collectors.toList());
            labelBO.setChildren(labelChildren);
            return labelBO;
        } else if (labels.size() == 1) {
            Label label = labels.get(0);
            LabelBO labelBO = new LabelBO();
            BeanUtils.copyProperties(label, labelBO);
            return labelBO;
        } else {
            String message = "标签labelId=" + labelId +  "不存在";
            throw new ResourceNotExistedException(message);
        }
    }

    @Override
    public void create(LabelRO labelRO) {
        Label label = new Label();
        BeanUtils.copyProperties(labelRO, label);
        label.setLevel(1);
        String introduce = labelRO.getIntroduce();
        String simpleIntroduce = introduce.length() > 100 ? introduce.substring(0, 100) : introduce;
        label.setSimpleIntroduce(simpleIntroduce);
        this.baseMapper.insert(label);
    }

    @Override
    public void createChild(LabelChildRO labelChildRO) {
        // 检查parentId
        Long parentId = labelChildRO.getParentId();
        LambdaQueryWrapper<Label> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Label::getLabelId, parentId).eq(Label::getLevel, 1);
        Long count = baseMapper.selectCount(queryWrapper);
        String message = "父标签：parentId=" + parentId + "不存在";
        assertUtils.businessState(count.equals(1L), new RelyDataNotExistedException(message));
        // 创建标签
        Label label = new Label();
        BeanUtils.copyProperties(labelChildRO, label);
        label.setLevel(2);
        String introduce = labelChildRO.getIntroduce();
        String simpleIntroduce = introduce.length() > 100 ? introduce.substring(0, 100) : introduce;
        label.setSimpleIntroduce(simpleIntroduce);
        this.baseMapper.insert(label);
    }

}
