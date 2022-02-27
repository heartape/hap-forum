package com.heartape.hap.business.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.heartape.hap.business.feign.TokenFeignService;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private TokenFeignService tokenFeignService;

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdBy",Long.class, (Long) tokenFeignService.getUid().getData());
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject,"updatedBy",Long.class, (Long) tokenFeignService.getUid().getData());
        this.strictUpdateFill(metaObject,"updatedTime", LocalDateTime.class, LocalDateTime.now());
    }
}