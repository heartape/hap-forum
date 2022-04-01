package com.heartape.hap.business.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.heartape.hap.business.feign.HapUserDetails;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Override
    public void insertFill(MetaObject metaObject) {
        HapUserDetails tokenInfo = tokenFeignService.getTokenInfo();
        this.strictInsertFill(metaObject, "uid",Long.class, tokenInfo.getUid());
        this.strictInsertFill(metaObject, "avatar",String.class, tokenInfo.getAvatar());
        this.strictInsertFill(metaObject, "nickname",String.class, tokenInfo.getNickname());
        this.strictInsertFill(metaObject, "childTargetName",String.class, tokenInfo.getNickname());
        this.strictInsertFill(metaObject, "profile",String.class, tokenInfo.getProfile());
        this.strictInsertFill(metaObject, "status",Boolean.class, true);
        this.strictInsertFill(metaObject, "createdBy",Long.class, tokenInfo.getUid());
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject,"updatedBy",Long.class, tokenInfo.getUid());
        this.strictInsertFill(metaObject, "updatedTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject,"updatedBy",Long.class, tokenFeignService.getUid());
        this.strictUpdateFill(metaObject,"updatedTime", LocalDateTime.class, LocalDateTime.now());
    }
}
