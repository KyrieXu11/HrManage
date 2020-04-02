package com.kyriexu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author KyrieXu
 * @since 2020/3/25 23:11
 **/
@EnableSwagger2
@EnableWebSecurity
@EnableConfigurationProperties
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = {"com.kyriexu.mapper"})
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class,args);
    }
}
