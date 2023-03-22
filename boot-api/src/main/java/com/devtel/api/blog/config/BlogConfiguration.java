package com.devtel.api.blog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class BlogConfiguration {
    private final KaKaoApiProperties kaKaoApiProperties;
    private final NaverApiProperties naverApiProperties;

    @Bean
    public WebClient kakaoWebClient() {
        return WebClient.builder().baseUrl(kaKaoApiProperties.getKakaoApiHost())
                .defaultHeader("Authorization","KakaoAK "+kaKaoApiProperties.getKakaoSecretKey()).build();
    }

    @Bean
    public WebClient naverWebClient() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverApiProperties.getNaverSecretId());
        headers.set("X-Naver-Client-Secret", naverApiProperties.getNaverSecretKey());
        return WebClient.builder().baseUrl(naverApiProperties.getNaverApiHost())
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.addAll(headers);
                }).build();
    }
}
