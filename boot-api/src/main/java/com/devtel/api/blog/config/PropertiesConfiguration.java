package com.devtel.api.blog.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties({
        KaKaoApiProperties.class,
        NaverApiProperties.class
})
@PropertySource({
        "classpath:config/kakao-api.properties",
        "classpath:config/naver-api.properties"
})
public class PropertiesConfiguration {
}
