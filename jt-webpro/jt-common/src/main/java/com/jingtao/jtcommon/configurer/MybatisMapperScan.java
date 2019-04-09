package com.jingtao.jtcommon.configurer;


import com.jingtao.jtcommon.constants.ProjectConstant;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

//import org.apache.log4j.Logger;


@Configuration
@AutoConfigureAfter(MybatisConfigurer.class)
//@ConditionalOnProperty(prefix = "common", name = "sqlSessionFactoryBean-open", havingValue = "true")
public class MybatisMapperScan {

    //private static final Logger log=Logger.getLogger(DataSourceContextHolder.class);

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage(ProjectConstant.MAPPER_PACKAGE+","+"com.jingtao.jtcommon.dao");

        //配置通用Mapper，详情请查阅官方文档
        Properties properties = new Properties();
        properties.setProperty("mappers", ProjectConstant.MAPPER_INTERFACE_REFERENCE);
        properties.setProperty("notEmpty", "false");//insert、update是否判断字符串类型!='' 即 test="str != null"表达式内是否追加 and str != ''
        properties.setProperty("IDENTITY", "MYSQL");
        properties.setProperty("ORDER", "BEFORE");//返回主键在执行语句之前，配合
//        properties.setProperty("ORDER","AFTER");
        mapperScannerConfigurer.setProperties(properties);

        return mapperScannerConfigurer;
    }
}
