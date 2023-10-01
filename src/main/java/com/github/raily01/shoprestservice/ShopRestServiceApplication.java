package com.github.raily01.shoprestservice;

import com.github.raily01.shoprestservice.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class ShopRestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopRestServiceApplication.class, args);
    }

}
