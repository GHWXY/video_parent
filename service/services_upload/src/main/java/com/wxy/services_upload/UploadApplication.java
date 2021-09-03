package com.wxy.services_upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author wxy
 * @date 2021年09月02日 11:31
 */
// 不让加载数据库相关信息
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class UploadApplication {
  public static void main(String[] args) {
    SpringApplication.run(UploadApplication.class, args);
  }
}
