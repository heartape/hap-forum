package com.heartape.hap.security.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.heartape.hap.security.utils.TokenUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdBy",Long.class, tokenUtil.getUid());
        this.strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject,"updatedBy",Long.class, tokenUtil.getUid());
        this.strictUpdateFill(metaObject,"updatedTime", LocalDateTime.class, LocalDateTime.now());
    }
}
