package com.jingtao.jtcommon.configurer;

import com.github.pagehelper.PageHelper;
import com.jingtao.jtcommon.constants.ProjectConstant;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Mybatis & Mapper & PageHelper 配置
 */
@Configuration
@EnableTransactionManagement
@AutoConfigureAfter(DataSourceConfigurer.class)
@ConditionalOnProperty(prefix = "common", name = "mybaties-open", havingValue = "true")
public class MybatisConfigurer implements TransactionManagementConfigurer {
    @Autowired
    @Qualifier("writeDataSource")
    private DataSource dataSource;

    @Autowired
    @Qualifier("readDataSource")
    private DataSource readDataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(roundRobinDataSouceProxy());
        factory.setTypeAliasesPackage(ProjectConstant.MODEL_PACKAGE);

        //配置分页插件，详情请查阅官方文档
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("pageSizeZero", "true");//分页尺寸为0时查询所有纪录不再执行分页
        properties.setProperty("reasonable", "true");//页码<=0 查询第一页，页码>=总页数查询最后一页
        properties.setProperty("supportMethodsArguments", "true");//支持通过 Mapper 接口参数来传递分页参数
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        //添加插件
//        factory.setPlugins(new Interceptor[]{pageHelper,new ParameterInterceptor()});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        SqlSessionFactory sqlSessionFactory=factory.getObject();
        sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);//Mybatis自动支持驼峰
        return sqlSessionFactory;
    }

    /**
     * 加入事物管理
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager makeDataSourceTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return manager;
    }

    /**
     * 多数据源
     *
     * @return
     */
    @Bean
    public AbstractRoutingDataSource roundRobinDataSouceProxy() {
        AbstractRoutingDataSource routingDataSource=new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return DataSourceContextHolder.getType();
            }
        };
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        // 写
        targetDataSources.put(DataSourceContextHolder.DATA_SOURCE_WRITE, dataSource);
        targetDataSources.put(DataSourceContextHolder.DATA_SOURCE_READ, readDataSource);
        routingDataSource.setDefaultTargetDataSource(dataSource);
        routingDataSource.setTargetDataSources(targetDataSources);
        return routingDataSource;
    }
    /**
     * 事务配置,考虑多数据源情况下
     * @return
     */
    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(roundRobinDataSouceProxy());
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager();
    }

}

