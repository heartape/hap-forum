package com.heartape.hap.business.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.heartape.hap.business.feign.TokenFeignService;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private TokenFeignService tokenFeignService;

    @Override
    public void insertFill(MetaObject metaObject) {
//        this.strictInsertFill(metaObject, "uid",Long.class, (Long) tokenFeignService.getUid().getData());
        this.strictInsertFill(metaObject, "uid",Long.class, 1L);
        this.strictInsertFill(metaObject, "avatar",String.class, "avatar");
        this.strictInsertFill(metaObject, "nickname",String.class, "nickname");
        this.strictInsertFill(metaObject, "profile",String.class, "profile");
        this.strictInsertFill(metaObject, "childrenId", List.class, new ArrayList<Long>());
        this.strictInsertFill(metaObject, "status",Boolean.class, true);
//        this.strictInsertFill(metaObject, "createdBy",Long.class, (Long) tokenFeignService.getUid().getData());
        this.strictInsertFill(metaObject, "createdBy",Long.class, 1L);
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
//        this.strictInsertFill(metaObject,"updatedBy",Long.class, (Long) tokenFeignService.getUid().getData());
        this.strictInsertFill(metaObject, "updatedBy",Long.class, 1L);
        this.strictInsertFill(metaObject, "updatedTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
//        this.strictUpdateFill(metaObject,"updatedBy",Long.class, (Long) tokenFeignService.getUid().getData());
        this.strictUpdateFill(metaObject,"updatedBy",Long.class, 1L);
        this.strictUpdateFill(metaObject,"updatedTime", LocalDateTime.class, LocalDateTime.now());
    }
}
