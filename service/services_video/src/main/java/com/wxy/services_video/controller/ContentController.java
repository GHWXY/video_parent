package com.wxy.services_video.controller;


import com.wxy.services_video.entity.Content;
import com.wxy.services_video.entity.vo.ContentVO;
import com.wxy.services_video.service.ContentService;
import com.wxy.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.PackagePrivate;
import org.apache.velocity.util.introspection.Uberspect;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * <p>
 * 作品表 前端控制器
 * </p>
 *
 * @author wxy
 * @since 2021-09-04
 */
@RestController
@RequestMapping("/services_video/content")
@Api(tags = "作品信息",value = "ContentController")
@CrossOrigin
public class ContentController {

    @Resource
    private ContentService contentService;

    /**
     * 保存作品信息
     * @param contentVO
     * @return
     */
    @ApiOperation(value = "保存作品信息")
    @PostMapping("/addContentInfo")
    public ResponseResult addContentInfo(@RequestBody ContentVO contentVO){
        //处理添加作品信息业务
        String id = contentService.saveContentInfo(contentVO);
        return ResponseResult.ok().data("contentId",id);
    }

    /**
     * 根据id获取作品简介信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id获取作品简介信息")
    @GetMapping("/getContentWithInfoId/{id}")
    public ResponseResult getContentWithInfoId(@PathVariable String id){
        ContentVO contentVO =  contentService.getContentWithInfoId(id);
        return ResponseResult.ok().data("contentVO",contentVO);

    }

    /**
     * 更新作品信息
     * @param contentVO
     * @return
     */
    @ApiOperation(value = "更新作品信息")
    @PostMapping("/updateContentInfo")
    public ResponseResult updateContentInfo(@RequestBody ContentVO contentVO){
        contentService.updateContentInfo(contentVO);
        return ResponseResult.ok();
    }



}

