package com.heartape.hap.business.service;

import com.heartape.hap.business.entity.bo.CreatorDetailBO;
import com.heartape.hap.business.entity.bo.CreatorProduceDataBO;

public interface ICreatorService {

    /**
     * 用户公开主页信息
     */
    CreatorDetailBO detail(Long uid);

    /**
     * 用户私有创作信息
     */
    CreatorProduceDataBO data(Long uid);
}
