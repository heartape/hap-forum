package com.heartape.hap.business.service.impl;

import com.heartape.hap.business.entity.Creator;
import com.heartape.hap.business.entity.bo.CreatorDetailBO;
import com.heartape.hap.business.entity.bo.CreatorProduceDataBO;
import com.heartape.hap.business.mapper.CreatorMapper;
import com.heartape.hap.business.service.ICreatorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatorServiceImpl implements ICreatorService {

    @Autowired
    private CreatorMapper creatorMapper;

    @Override
    public CreatorDetailBO detail(Long uid) {
        Creator creator = creatorMapper.selectById(uid);
        CreatorDetailBO creatorDetailBO = new CreatorDetailBO();
        BeanUtils.copyProperties(creator, creatorDetailBO);
        return creatorDetailBO;
    }

    @Override
    public CreatorProduceDataBO data(Long uid) {
        CreatorProduceDataBO creatorProduceDataBO = new CreatorProduceDataBO();
        return creatorProduceDataBO;
    }
}
