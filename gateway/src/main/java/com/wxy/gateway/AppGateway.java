package com.wxy.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wxy
 * @date 2021年10月07日 14:23
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AppGateway {
  public static void main(String[] args) {
    SpringApplication.run(AppGateway.class,args);
  }
}
