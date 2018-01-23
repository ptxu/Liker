package com.liker.services.http.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @ClassName: AppConfig
 * @Description: spring context 的配置文件
 * @author xupengtao
 * @date 2018年1月12日 下午6:29:58
 *
 */
@Configuration
@EnableWebMvc
@ImportResource({ "classpath*:/applicationContext.xml" })
public class AppConfig {
}
