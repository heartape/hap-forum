package com.heartape.hap.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.entity.Creator;

public interface ICreatorService extends IService<Creator> {
    String nickname(Long uid);
}
