package com.eaglebank.aggregate;

import com.eaglebank.aggregate.security.RsaKeyProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProps.class)
@EnableCaching
@EnableScheduling
public class AggregateApplication {
    public static void main(String[] args) {
        SpringApplication.run(AggregateApplication.class, args);
    }
}