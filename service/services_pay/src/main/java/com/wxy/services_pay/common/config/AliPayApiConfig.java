package com.wxy.services_pay.common.config;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.wxy.services_pay.common.properties.AliPayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
@Configuration
public class AliPayApiConfig {
    @Autowired
    private AliPayProperties aliPayProperties;
    private static final String JSON = "json";
    private static final String RSA2 = "RSA2";
    private static final String CHARSET = "UTF-8";
    public AlipayClient getAliPayClient(){
        DefaultAlipayClient alipayClient = new DefaultAlipayClient(
                aliPayProperties.getServerUrl(),
                aliPayProperties.getAppId(),
                aliPayProperties.getPrivateKey(),
                JSON,
                CHARSET,
                aliPayProperties.getPublvicKey(),
                RSA2
        );
        return alipayClient;
    }
}
