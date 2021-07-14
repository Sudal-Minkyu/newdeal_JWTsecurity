package com.broadwave.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 호스트링크주입
    @Value("${newdeal.api.front_url}")
    private String front_url;

    //CORS에러 No 'Access-Control-Allow-Origin' header is present on the requested resource. 해결
    @Override
    public void addCorsMappings(CorsRegistry registry) {
             registry.addMapping("/auth/**") // auth 경로의대한 모든권한 허용
                     .allowedOrigins("http://localgost:8010","http://"+front_url,"http://192.168.0.24:8010"); // 허용 url 지겸님 ip http://192.168.0.24:8010
    }

}