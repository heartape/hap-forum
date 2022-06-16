package com.heartape.hap.service;

import com.heartape.hap.entity.bo.CreatorDetailBO;
import com.heartape.hap.entity.bo.CreatorProduceDataBO;

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
