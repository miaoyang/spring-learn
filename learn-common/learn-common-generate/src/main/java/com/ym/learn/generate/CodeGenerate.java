package com.ym.learn.generate;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/29 19:59
 * @Desc: 模板代码生成器
 */
public class CodeGenerate {
    /**
     * 数据库表
     */
    private static final String[] TABLE_NAMES = new String[]{
            "media_files"
    };

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Velocity
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setFileOverride(true);
        //生成路径
        gc.setOutputDir(System.getProperty("user.dir") + "/learn-common/learn-common-generate/src/main/java");
        gc.setAuthor("yangmiao");
        gc.setOpen(false);
        gc.setSwagger2(false);
        gc.setServiceName("%sService");
        gc.setBaseResultMap(true);
        gc.setBaseColumnList(true);
        mpg.setGlobalConfig(gc);

        DataSourceConfig dataSource = CodeConfig.getDataSource("learn-spring");
        mpg.setDataSource(dataSource);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("generate");
        pc.setParent("com.ym.learn.generate");

        pc.setServiceImpl("service.impl");
        pc.setXml("mapper");
        pc.setEntity("model.po");
        mpg.setPackageInfo(pc);

        // 设置模板
        TemplateConfig tc = new TemplateConfig();
        mpg.setTemplate(tc);

        // 设置策略
        StrategyConfig strategyConfig = CodeConfig.getStrategyConfig(TABLE_NAMES, pc);
        mpg.setStrategy(strategyConfig);

        mpg.execute();
    }
}
