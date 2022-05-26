package com.heartape.hap.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.entity.Creator;
import com.heartape.hap.mapper.CreatorMapper;
import com.heartape.hap.service.ICreatorService;
import org.springframework.stereotype.Service;

@Service
public class CreatorServiceImpl extends ServiceImpl<CreatorMapper, Creator> implements ICreatorService {
    @Override
    public String nickname(Long uid) {
        LambdaQueryWrapper<Creator> queryWrapper = new QueryWrapper<Creator>().lambda();
        queryWrapper.select(Creator::getNickname).eq(Creator::getUid, uid);
        Creator creator = baseMapper.selectOne(queryWrapper);
        return creator.getNickname();
    }
}
