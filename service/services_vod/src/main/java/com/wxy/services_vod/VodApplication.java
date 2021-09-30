package com.wxy.services_vod;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wxy
 * @date 2021年09月05日 21:04
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.wxy"})
public class VodApplication {
  public static void main(String[] args) {
    SpringApplication.run(VodApplication.class,args);
  }
}
