package com.heartape.hap.business.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MybatisPlusGenerator {

    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        FastAutoGenerator.create("localhost:3306/hap", "root", "root")
                .globalConfig(builder -> {
                    builder.author("heartape") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(dir + "/src/main/java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    // todo:设置父包名
                    builder.parent("com.heartape.hap")
                            // todo:设置父包模块名
                            .moduleName("hap")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, dir + "/src/main/resource")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    // todo:设置需要生成的表名
                    builder.addInclude("t_simple")
                            // todo:设置过滤表前缀
                            .addTablePrefix("t_", "c_");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
