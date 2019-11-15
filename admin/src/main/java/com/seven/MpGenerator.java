package com.seven;



import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MpGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();//Generator下的包

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");//当前项目的目录G:\projects\huo

        //String projectPath="G:\\projects\\huo\\admin\\src\\main\\java";

        //当前目录下G:\projects\huo\src\main\java
        //gc.setOutputDir(projectPath + "/src/main/java");
        //指定目录下G:\projects\huo\admin\src\main\java
        gc.setOutputDir(projectPath);

        gc.setAuthor("huo");
        gc.setOpen(false);//当代码生成之后是否打开所在的文件夹
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/huodian?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8");
        dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.seven");
        //pc.setModuleName(scanner("模块名"));//自定义包名
        pc.setMapper("dao");
        pc.setService("service");
        pc.setController("controller");
        pc.setEntity("model");


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // xxxMapper.xml
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                /*return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;*/

                //根据自己的位置修改
                return projectPath + "/src/main/resources/mappers/" +tableInfo.getEntityName()
                        + "Mapper" + StringPool.DOT_XML;
            }
        });

        //控制层
        templatePath = "/templates/controller.java.ftl";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = projectPath + "/src/main/java/com/seven/" + "controller";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getControllerName());
                return entityFile;
            }
        });

        //业务层
        templatePath = "/templates/service.java.ftl";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = projectPath + "/src/main/java/com/seven/" + "service";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getServiceName());
                return entityFile;
            }
        });

        //业务实现层
        templatePath = "/templates/serviceImpl.java.ftl";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = projectPath + "/src/main/java/com/seven/" + "service/impl";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getServiceImplName());
                return entityFile;
            }
        });

        //数据层
        templatePath = "/templates/mapper.java.ftl";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = projectPath + "/src/main/java/com/seven/"+"dao";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getMapperName());
                return entityFile;
            }
        });

        //实体类层
        templatePath = "/templates/entity.java.ftl";
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 + pc.getModuleName()
                String expand = projectPath + "/src/main/java/com/seven/"+"model";
                String entityFile = String.format((expand + File.separator + "%s" + ".java"), tableInfo.getEntityName());
                return entityFile;
            }
        });

        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        mpg.setPackageInfo(pc);
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 数据库表策略配置
        StrategyConfig strategy = new StrategyConfig();
        //数据库表映射到实体的命名策略 下划线改驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //设置生成的实体类继承父类
        //strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        //【实体】是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(true);
        //生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        // 设置controller继承的父类
        //strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        // 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        strategy.setInclude("t_code");//数据库表名
        strategy.setControllerMappingHyphenStyle(true);//驼峰转字符
        //根据你的表名来建对应的类名，如果你的表名没有什么下划线，比如test，那么你就可以取消这一步
        strategy.setTablePrefix("t_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
