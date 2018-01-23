/**
 * Copyright: Copyright (c) 2016 
 * Company:深圳市深网视界科技有限公司
 * 
 * @author dell
 * @date 2016年11月11日 上午10:19:09
 * @version V1.0
 */
package com.liker.services.http.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: SwaggerConfig
 * @Description: SwaggerConfig
 * @author xupengtao
 * @date 2018年1月12日 下午6:32:27
 *
 */
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 
     * @Title: createRestApi
     * @Description: Every Docket bean is picked up by the swagger-mvc framework - allowing for multiple swagger groups i.e. same code base multiple swagger resource listings.
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
    }

    /**
     * @Title: apiInfo
     * @Description: ApiInfo
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Liker APIs").version("v1.0").build();
    }
}
