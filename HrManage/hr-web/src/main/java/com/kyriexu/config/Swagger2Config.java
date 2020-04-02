package com.kyriexu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author KyrieXu
 * @since 2020/3/29 16:16
 **/
@Configuration
public class Swagger2Config {

    private final static String BASE_PACKAGE = "com.kyriexu";

    private final static String VERSION = "v1.0-SNAPSHOT";

    private final static String TERMS_OF_SERVICE_URL="";

    private final static String TITLE="人事管理系统";

    private final static String DESCRIPTION="人事管理系统";

    @Bean
    public Docket ApiDoc(){
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                // 可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title(TITLE)
                        .description(DESCRIPTION)
                        .version(VERSION)
                        // 文档的lience信息
                        .termsOfServiceUrl(TERMS_OF_SERVICE_URL)
                        .build());
    }
}
