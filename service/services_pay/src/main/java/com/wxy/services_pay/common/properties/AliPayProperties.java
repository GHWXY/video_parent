package com.wxy.services_pay.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "pay.alibaba")
public class AliPayProperties {
    private String appId;
    //私钥
    private String privateKey;
    //公钥
    private String publvicKey;
    //支付宝服务地址
    private String serverUrl;
    //回调地址
    private String returnUrl;
    //异步回调
    private String notyfyUrl;
}
