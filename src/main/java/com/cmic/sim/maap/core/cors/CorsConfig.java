package com.cmic.sim.maap.core.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 测试环境
        corsConfiguration.addAllowedOrigin("http://120.196.212.10:8080");
        corsConfiguration.addAllowedOrigin("http://120.196.212.10");
        //生产环境
        corsConfiguration.addAllowedOrigin("http://120.232.169.177");
        corsConfiguration.addAllowedOrigin("https://120.232.169.177");
        //生产环境域名
        corsConfiguration.addAllowedOrigin("http://econtract.cmpassport.com");
        corsConfiguration.addAllowedOrigin("https://econtract.cmpassport.com");
        //业务方
        corsConfiguration.addAllowedOrigin("http://china2019.vip:8080");
        corsConfiguration.addAllowedOrigin("https://china2019.vip:8080");
        //本地测试
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        corsConfiguration.addAllowedOrigin("http://localhost:8081");
        // 2允许任何头
        corsConfiguration.addAllowedHeader("*");
        // 3允许任何方法（post、get等）
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //所有前端请求
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
