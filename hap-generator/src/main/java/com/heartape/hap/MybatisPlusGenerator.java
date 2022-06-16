package com.heartape.hap;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MybatisPlusGenerator {

   public static void main(String[] args) {
       String dir = System.getProperty("user.dir");
       FastAutoGenerator.create("jdbc:mysql://localhost:3306/hap_forum", "root", "root")
               .globalConfig(builder -> {
                   builder.author("heartape") // 设置作者
                           .enableSwagger() // 开启 swagger 模式
                           .fileOverride() // 覆盖已生成文件
                           // todo:设置路径
                           .outputDir(dir + "/hap-system/src/main/java"); // 指定输出目录
               })
               .packageConfig(builder -> {
                   // todo:设置父包名
                   builder.parent("com.heartape.hap")
                           // todo:设置父包模块名
                           .moduleName("system")
                           // todo:设置路径
                           .pathInfo(Collections.singletonMap(OutputFile.mapperXml, dir + "/hap-system/src/main/resources/mapper")); // 设置mapperXml生成路径
               })
               .strategyConfig(builder -> {
                   builder
                           // todo:设置需要生成的表名
                           .addInclude("article","article_comment")
                           // todo:设置过滤表前缀
                           .addTablePrefix("t_", "c_")
                           .entityBuilder()
                           .superClass(BaseEntity.class)
                           .addSuperEntityColumns("status","created_time","updated_time")
                           .idType(IdType.ASSIGN_ID)
                           .enableLombok()
                           .enableTableFieldAnnotation();
               })
               .templateEngine(new FreemarkerTemplateEngine())
               .execute();
   }
}
