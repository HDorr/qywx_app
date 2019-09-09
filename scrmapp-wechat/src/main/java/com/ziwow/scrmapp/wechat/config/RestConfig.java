package com.ziwow.scrmapp.wechat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.RestTemplate;

/**
 * @author songkaiqi
 * @since 2019/06/09/下午2:09
 */
@Configuration
@ControllerAdvice
public class RestConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
