package com.wxy.services_video.client;

import com.wxy.utils.ResponseResult;
import org.springframework.stereotype.Component;

/**
 * @author wxy
 * @date 2021年10月06日 17:00
 */
@Component
public class VodFeignClientFallBack implements VodClient {

    @Override
    public ResponseResult deleteAliyunVideo(String videoId) {
        return ResponseResult.error().message("熔断处理-调用超时");
    }

    @Override
    public ResponseResult testVod(String id) {
        return ResponseResult.error().message("熔点降级-调用超时");
    }
}
