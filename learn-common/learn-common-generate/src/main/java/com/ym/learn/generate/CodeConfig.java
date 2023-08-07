package com.ym.learn.generate;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import javax.sql.DataSource;

import java.util.Arrays;

import static com.ym.learn.generate.CodeGenerateConstant.*;

/**
 * @Author: Yangmiao
 * @Date: 2023/5/29 20:18
 * @Desc:
 */
public class CodeConfig {
    /**
     * 数据库配置
     * @param databaseName
     * @return
     */
    public static DataSourceConfig getDataSource(String databaseName){
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl("jdbc:mysql://localhost:3306/" + databaseName
                + "?serverTimezone=UTC&useUnicode=true&useSSL=false&characterEncoding=utf8");
        dsc.setUsername(DB_USERNAME);
        dsc.setPassword(DB_PASSWORD);
        dsc.setDriverName(DB_DRIVER_CLASS_NAME);
        return dsc;
    }

    /**
     * 策略配置
     * @param tableArrays 数据库表集合
     * @param pc
     * @return
     */
    public static StrategyConfig getStrategyConfig(String []tableArrays, PackageConfig pc){
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(tableArrays);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        // Boolean类型字段是否移除is前缀处理
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        strategy.setRestControllerStyle(true);

        // 自动填充字段配置
        strategy.setTableFillList(Arrays.asList(
                new TableFill("create_date", FieldFill.INSERT),
                new TableFill("change_date", FieldFill.INSERT_UPDATE),
                new TableFill("modify_date", FieldFill.UPDATE)
        ));
        return strategy;
    }
    
}
