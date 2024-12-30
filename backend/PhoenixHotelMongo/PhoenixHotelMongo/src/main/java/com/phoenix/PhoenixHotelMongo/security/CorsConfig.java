package com.phoenix.PhoenixHotelMongo.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")              // All URL Paterns
                        .allowedMethods("GET","POST","PUT","DELETE")   // 4 types od request methods
                        .allowedOrigins("*");                     // Any domain is allowed to request
            }
        };
    }

}
