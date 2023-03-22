package com.devtel.api.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties
public class KaKaoApiProperties {
    private String kakaoApiHost;
    private String kakaoSecretKey;
}
