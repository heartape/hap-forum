package com.heartape.hap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.heartape.hap.entity.LinkGuide;
import com.heartape.hap.entity.bo.LinkGuideBO;
import com.heartape.hap.mapper.LinkGuideMapper;
import com.heartape.hap.service.ILinkGuideService;
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
public class LinkGuideServiceImpl extends ServiceImpl<LinkGuideMapper, LinkGuide> implements ILinkGuideService {

    @Override
    public List<LinkGuideBO> showHot() {
        LambdaQueryWrapper<LinkGuide> queryWrapper = new QueryWrapper<LinkGuide>().lambda();
        // todo:先取出top的内容,再取出范围内的order对应的数据,再根据时间进行排序
        queryWrapper.select(LinkGuide::getGuideId, LinkGuide::getTitle, LinkGuide::getPath).orderByDesc(LinkGuide::getTopping).orderByDesc(LinkGuide::getSequence);
        PageHelper.startPage(1,10);
        List<LinkGuide> list = baseMapper.selectList(queryWrapper);
        return list.stream().map(linkGuide -> {
            LinkGuideBO linkGuideBO = new LinkGuideBO();
            BeanUtils.copyProperties(linkGuide, linkGuideBO);
            return linkGuideBO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<LinkGuideBO> showList(Integer page, Integer size) {
        LambdaQueryWrapper<LinkGuide> queryWrapper = new QueryWrapper<LinkGuide>().lambda();
        // todo:先取出top的内容,再取出范围内的order对应的数据,再根据时间进行排序
        queryWrapper.select(LinkGuide::getGuideId, LinkGuide::getTitle, LinkGuide::getPath).orderByDesc(LinkGuide::getTopping).orderByDesc(LinkGuide::getSequence);
        PageHelper.startPage(page,size);
        List<LinkGuide> list = baseMapper.selectList(queryWrapper);
        return list.stream().map(linkGuide -> {
            LinkGuideBO linkGuideBO = new LinkGuideBO();
            BeanUtils.copyProperties(linkGuide, linkGuideBO);
            return linkGuideBO;
        }).collect(Collectors.toList());
    }

    @Override
    public void create() {
        // baseMapper.insert();
    }
}
