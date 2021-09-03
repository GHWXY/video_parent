package com.wxy.services_upload.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wxy
 * @date 2021年09月02日 11:35
 */
@Component
public class OSSConstant implements InitializingBean {
    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.bucketName}")
    private String bucketName;

    public static String ENDPOINT;
    public static String ASSESS_KEY_ID;
    public static String ASSESS_KEY_SECRET;
    public static String BUCKET_NAME;


    /**
     * 在属性文件加载完毕后属性也设置完毕之后, 会自动调用
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT = endpoint;
        ASSESS_KEY_ID = accessKeyId;
        ASSESS_KEY_SECRET = accessKeySecret;
        BUCKET_NAME = bucketName;

    }
}
