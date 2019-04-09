package com.jingtao.jtcommon.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class DataSourceConfigurer {
    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name="writeDataSource",destroyMethod = "close",initMethod = "init")
    @Primary
    @ConfigurationProperties(prefix = "spring.write.datasource")
    public DataSource writeDataSource(){
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name="readDataSource")
    @ConfigurationProperties(prefix = "spring.read.datasource")
    public DataSource readDataSource(){
        return DataSourceBuilder.create().type(dataSourceType).build();
    }
}
