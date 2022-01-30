package com.heartape.hap.admin.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.heartape.hap.admin.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdBy",Long.class, securityUtils.getUid());
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject,"updatedBy",Long.class, securityUtils.getUid());
        this.strictUpdateFill(metaObject,"updatedTime", LocalDateTime.class, LocalDateTime.now());
    }
}
