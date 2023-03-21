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
            .setMaxConnTotal(50)            //최대 커넥션 수
            .setMaxConnPerRoute(20).build();//각 호스트(IP와 Port의 조합)당 커넥션 풀에 생성가능한 커넥션 수

        factory.setHttpClient(httpClient);
        factory.setReadTimeout(5000); // read timeout
        factory.setConnectTimeout(3000); // connection timeout

        return new RestTemplate(factory);
    }
}
