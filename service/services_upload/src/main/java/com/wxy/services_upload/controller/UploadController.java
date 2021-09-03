package com.wxy.services_upload.controller;

import com.wxy.services_upload.service.UploadService;
import com.wxy.utils.ResponseResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author wxy
 * @date 2021年09月02日 13:18
 */
@RestController
@RequestMapping("/service_upload/file")
@CrossOrigin
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping("/ossUploadFile")
    public ResponseResult ossUploadFile(MultipartFile file){
        String url = uploadService.uploadFile(file);
        return ResponseResult.ok().data("url",url);
    }
}
