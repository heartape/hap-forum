package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.heartape.hap.business.entity.LinkGuide;
import com.heartape.hap.business.entity.bo.LinkGuideBO;
import com.heartape.hap.business.mapper.LinkGuideMapper;
import com.heartape.hap.business.service.ILinkGuideService;
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
    public List<LinkGuideBO> showList(Integer page, Integer size) {
        LambdaQueryWrapper<LinkGuide> queryWrapper = new QueryWrapper<LinkGuide>().lambda();
        queryWrapper.select(LinkGuide::getGuideId, LinkGuide::getTitle, LinkGuide::getPath).orderByDesc(LinkGuide::getTopping).orderByDesc(LinkGuide::getSequence);
        PageHelper.startPage(page,size);
        // todo:现根据top,order排序,再根据热度进行排序
        List<LinkGuide> list = baseMapper.selectList(queryWrapper);
        return list.stream().map(linkGuide -> {
            LinkGuideBO linkGuideBO = new LinkGuideBO();
            BeanUtils.copyProperties(linkGuide, linkGuideBO);
            return linkGuideBO;
        }).collect(Collectors.toList());
    }

    @Override
    public void create() {
        baseMapper.insert();
    }
}
