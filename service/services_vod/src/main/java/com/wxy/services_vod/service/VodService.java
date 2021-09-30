package com.wxy.services_vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface VodService {
    //上传视频
    String uploadVideo(MultipartFile file);
    //删除视频
    void deleteVideo(String videoId);
}


