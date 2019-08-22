package com.cmic.sim.maap.core.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author haiqiang
 * @date 2019/2/21 15:00
 */
@Configuration
@MapperScan(basePackages = {"com.cmic.econtract.system.dao"})
public class MybatisPlusConfig {

   @Autowired
    ContractDataSourceProperties contractDataSourceProperties;

   private DruidDataSource contractDataSource(){
       DruidDataSource dataSource=new DruidDataSource();
       contractDataSourceProperties.config(dataSource);
       return dataSource;
   }

    /**
     * 单数据源配置
     * @return
     */
   @Bean
    public DruidDataSource singleDatasource(){
       return contractDataSource();
   }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 乐观锁mybatis插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
