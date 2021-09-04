package com.wxy.services_video.controller;


import com.wxy.services_video.entity.vo.ChapterVO;
import com.wxy.services_video.entity.vo.ContentVideoInfoVO;
import com.wxy.services_video.service.ContentVideoService;
import com.wxy.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 作品视频 前端控制器
 * </p>
 *
 * @author wxy
 * @since 2021-09-04
 */
@RestController
@RequestMapping("/services_video/content-video")
@Api(tags = "作品小节",value = "ContentVideoController")
@CrossOrigin
public class ContentVideoController {
    @Resource
    private ContentVideoService contentVideoService;

    /**
     * 新增小节
     * @param videoInfoForm
     * @return
     */
    @ApiOperation(value = "新增小节")
    @PostMapping("/addContentVideo")
    public ResponseResult addContentVideo(@RequestBody ContentVideoInfoVO videoInfoForm){
        contentVideoService.addContentVideo(videoInfoForm);
        return ResponseResult.ok();
    }

    /**
     * 根据id查询小节
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询小节")
    @GetMapping("/getVideoInfoById/{id}")
    public ResponseResult getVideoInfoById(@PathVariable String id){
        ContentVideoInfoVO contentVideoInfoVO = contentVideoService.getVideoInfoById(id);
        return ResponseResult.ok().data("item",contentVideoInfoVO);
    }

    /**
     * 更新小节
     * @param videoInfoForm
     * @return
     */
    @ApiOperation(value = "更新小节")
    @PostMapping("/updateContentVideo")
    public ResponseResult updateContentVideo(@RequestBody ContentVideoInfoVO videoInfoForm){
        contentVideoService.updateContentVideo(videoInfoForm);
        return ResponseResult.ok();
    }

    /**
     * 根据id删除小节
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除小节")
    @PostMapping("/deleteContentVideoInfoById/{id}")
    public ResponseResult deleteContentVideoInfo(@PathVariable String id){
        boolean result = contentVideoService.deleteContentVideoInfoById(id);
        if (result){
            return ResponseResult.ok();
        }else {
            return ResponseResult.error().message("删除失败");
        }
    }
}

