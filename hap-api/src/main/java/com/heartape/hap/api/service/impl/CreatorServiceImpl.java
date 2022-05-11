package com.heartape.hap.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heartape.hap.api.entity.Creator;
import com.heartape.hap.api.mapper.CreatorMapper;
import com.heartape.hap.api.service.ICreatorService;
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
