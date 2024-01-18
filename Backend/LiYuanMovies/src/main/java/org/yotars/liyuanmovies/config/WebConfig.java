package org.yotars.liyuanmovies.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * Web配置类
 *
 * @author Yotars
 * @since 2024-01-06 15:25:48
 */
@Configuration
public class WebConfig {
    /**
     * 设置web跨域
     * @param registry
     */
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*")
                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}


