package com.wxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.wxy"})
@EnableDiscoveryClient
@MapperScan("com.wxy.services_cms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}