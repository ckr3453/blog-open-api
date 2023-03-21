package com.kakao.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * packageName : com.kakao.config
 * fileName    : RestTemplateConfiguration
 * author      : ckr
 * date        : 2023/03/19
 * description :
 */
@Configuration
class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
            .setMaxConnTotal(50)
            .setMaxConnPerRoute(20).build();

        factory.setHttpClient(httpClient);
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(3000);

        return new RestTemplate(factory);
    }
}
