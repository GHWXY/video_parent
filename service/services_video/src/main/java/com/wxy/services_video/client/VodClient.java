package com.wxy.services_video.client;

import com.wxy.utils.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="service-vod",fallback = VodFeignClientFallBack.class)
@Component
public interface VodClient {
    @PostMapping(value = "/services_vod/vod/delete-vod/{videoId}")
    public ResponseResult deleteAliyunVideo(@PathVariable("videoId") String videoId);

    @GetMapping(value = "/services_vod/vod/testVod/{id}")
    public ResponseResult testVod(@PathVariable String id);

}
