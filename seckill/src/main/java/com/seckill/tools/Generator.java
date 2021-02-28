package com.seckill.tools;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class Generator {

    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();

        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true)  //支持Ar模式
                .setAuthor("Wei")
                .setOpen(false)
                .setFileOverride(true)
                .setOutputDir("src\\main\\java\\")
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true)// XML columList
                .setServiceName("I%sService")
                .setMapperName("%sDao");
        autoGenerator.setGlobalConfig(config);

        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.jdbc.Driver")
                .setUsername("root")
                .setPassword("123456")
                .setUrl("jdbc:mysql://127.0.0.1:3306/seckill?characterEncoding=utf8&useUnicode=true&useSSL=false");
        autoGenerator.setDataSource(dataSourceConfig);

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setTablePrefix("t_")// 此处可以修改为您的表前缀
                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                .setEntityTableFieldAnnotationEnable(true)
                .setInclude(new String[]{"t_seckill_order"}) //需要生成的表
                .setEntityLombokModel(true) //Lombok
                .setRestControllerStyle(true);
        autoGenerator.setStrategy(strategyConfig);

        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent("com.seckill")
                .setEntity("beans.po")
                .setMapper("dao")
                .setService("service")
                .setController("controller")// 这里是控制器包名，默认 web
                .setServiceImpl("service.impl")
        ;
        autoGenerator.setPackageInfo(packageConfig);

        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null); //设置不生成xml

        autoGenerator.setTemplate(templateConfig);

        autoGenerator.execute();
    }

}
