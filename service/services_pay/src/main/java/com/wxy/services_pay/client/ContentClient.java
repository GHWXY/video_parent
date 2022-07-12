package com.wxy.services_pay.client;

import entity.ContentWebVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name="service-video")
public interface ContentClient {
    //根据作品id查询课程信息
    @PostMapping("/services_video/content/getContentInfoOrder/{id}")
    public ContentWebVO getContentInfoOrder(@PathVariable("id") String id);
}

