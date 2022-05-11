package com.heartape.hap.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heartape.hap.api.entity.Creator;

public interface ICreatorService extends IService<Creator> {
    String nickname(Long uid);
}
