package com.wxy.services_upload.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.wxy.services_upload.service.UploadService;
import com.wxy.services_upload.utils.OSSConstant;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author wxy
 * @date 2021年09月02日 13:19
 */
@Service
public class UploadServiceImpl implements UploadService {




    @Override
    public String uploadFile(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = OSSConstant.ENDPOINT;
        // 云账号AccessKey有所有API访问权限
        String accessKeyId =OSSConstant.ASSESS_KEY_ID;
        String accessKeySecret = OSSConstant.ASSESS_KEY_SECRET;
        String bucketName = OSSConstant.BUCKET_NAME;
        // 创建OSSClient实例。
        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            //处理文件名称
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            String fileName = uuid+file.getOriginalFilename();
            /*把同一天上传的文件 放到同一个文件夹当中  2020/10/1/fileName*/
            String date = new DateTime().toString("yyyy/MM/dd");
            fileName = date+"/"+fileName;
            ossClient.putObject(bucketName, fileName, inputStream);
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return  url;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
        return null;
    }
}
