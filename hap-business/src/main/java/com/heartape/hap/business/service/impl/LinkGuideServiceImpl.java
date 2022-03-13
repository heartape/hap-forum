package com.heartape.hap.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.heartape.hap.business.entity.LinkGuide;
import com.heartape.hap.business.mapper.LinkGuideMapper;
import com.heartape.hap.business.service.ILinkGuideService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<LinkGuide> showList(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        // todo:现根据top,order排序,再根据热度进行排序
        return query().orderByDesc("topping", "sequence").list();
    }
}
