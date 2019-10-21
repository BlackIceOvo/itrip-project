package com.itrip.biz.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.itrip.biz.config.JdbcProperties;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


/**
 * @author ice
 */
@Configuration
@MapperScan("com.itrip.dao.mapper")
@EnableTransactionManagement
public class MybatisConfig {
    @Autowired
    JdbcProperties propertiesConfig;

    /**
     * 设置数据源
     *
     * @return
     */
    @Bean
    public DataSource dataSource() {

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(propertiesConfig.getDriver());
        druidDataSource.setUrl(propertiesConfig.getUrl());
        druidDataSource.setUsername(propertiesConfig.getUserName());
        druidDataSource.setPassword(propertiesConfig.getPassWord());
        druidDataSource.setDefaultAutoCommit(false);
        return druidDataSource;
    }

    /**
     * 配置MyBatis  SqlSessionFactoryBean
     *
     * @return
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        return sqlSessionFactoryBean;
    }

    /**
     * 事务数据源
     *
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }
}
