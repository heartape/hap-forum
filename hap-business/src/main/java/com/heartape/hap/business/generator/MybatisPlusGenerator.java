package com.heartape.hap.business.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.heartape.hap.business.entity.BaseEntity;

import java.util.Collections;

public class MybatisPlusGenerator {

    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/hap_forum", "root", "root")
                .globalConfig(builder -> {
                    builder.author("heartape") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(dir + "/hap-business/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    // todo:设置父包名
                    builder.parent("com.heartape.hap")
                            // todo:设置父包模块名
                            .moduleName("business")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, dir + "/hap-business/src/main/resource")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder
                            // todo:设置需要生成的表名
                            .addInclude("article_0")
                            // todo:设置过滤表前缀
                            .addTablePrefix("t_", "c_")
                            .entityBuilder()
                            .superClass(BaseEntity.class)
                            .logicDeleteColumnName("status")
                            .addSuperEntityColumns("status","created_by","created_time","updated_by","updated_time")
                            .idType(IdType.ASSIGN_ID)
                            .enableLombok().
                            enableTableFieldAnnotation();
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
