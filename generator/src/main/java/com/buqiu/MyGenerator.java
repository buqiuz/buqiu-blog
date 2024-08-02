package com.buqiu;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;

public class MyGenerator {
    public static void main(String[] args) {// 使用 FastAutoGenerator 快速配置代码生成器
        FastAutoGenerator.create("jdbc:mysql://buqiu.icu:3306/blog", "root", "CS2313684085cs")
                .globalConfig(builder -> {
                    builder.author("不秋") // 设置作者
                            .disableOpenDir() // 不允许自动打开输出目录
//                            .enableSwagger()  // 启用 Swagger
                            .enableSpringdoc() // 启用 Springdoc
                            .commentDate("yyyy-MM-dd HH:mm:ss")
                            .outputDir(Paths.get(System.getProperty("user.dir")) + "/blog/src/main/java"); // 指定输出目录

                })
                .packageConfig(builder -> {
                    builder.parent("com.buqiu.blog")
                            .entity("domain.entity") //设置entity包名

                    ; // 设置父包名
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user") // 设置需要生成的表名
                            // 实体类
                            .entityBuilder()
                            .enableLombok() // 启用 Lombok
//                            .enableActiveRecord() // 启用 ActiveRecord 模式，实体类继承 Model然后可以直接使用 ActiveRecord 模式的方法
                            .enableFileOverride()// 覆盖已有entity文件
                            .enableTableFieldAnnotation() // 启用字段注解
//                            .javaTemplate("templates/entity.java")

                            // controller
                            .controllerBuilder()
//                            .enableFileOverride() // 覆盖已有controller文件
//                            .enableHyphenStyle() // 开启驼峰转连字符
                            .enableRestStyle() // 开启生成@RestController 控制器

                            // service
                            .serviceBuilder()
//                            .enableFileOverride() // 覆盖已有service文件

                            // mapper
                            .mapperBuilder()
                            .enableFileOverride() // 覆盖已有mapper文件
//                            .enableMapperAnnotation() // 启用 Mapper 注解(已弃用)
                            .disableMapperXml(); // 禁用生成 Mapper XML 文件

                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 模板引擎
                .execute(); // 执行生成
    }
}
