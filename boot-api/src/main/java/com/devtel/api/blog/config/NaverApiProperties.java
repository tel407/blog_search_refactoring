package com.devtel.api.blog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties
public class NaverApiProperties {
    private String naverApiHost;
    private String naverSecretKey;
    private String naverSecretId;
}

