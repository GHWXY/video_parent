package com.wxy.services_vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.wxy.services_base.exception.FmException;
import com.wxy.services_vod.service.VodService;
import com.wxy.services_vod.utils.AliyunVodSDKUtils;
import com.wxy.services_vod.utils.VodConstant;
import com.wxy.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.MultimediaInfo;
import ws.schild.jave.MultimediaObject;

import java.io.File;


@RestController
@RequestMapping("/services_vod/vod")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;
    @PostMapping("upload")
    public ResponseResult uploadVideo(@RequestParam("file") MultipartFile file)
            throws Exception {
        String videoId = vodService.uploadVideo(file);
        /*File file1 = multipartFileToFile(file);
        long videoTime = getVideoTime(file1);*/
        return ResponseResult.ok().message("视频上传成功").data("videoId", videoId);
    }

    public static File multipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码
        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取视频时长：秒
     */
    public static long getVideoTime(File file) {
        try {
            MultimediaObject instance = new MultimediaObject(file);
            MultimediaInfo result = instance.getInfo();
            long ls = result.getDuration() / 1000;
            return ls;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0l;
    }

    @PostMapping("/delete-vod/{videoId}")
    public ResponseResult removeVideo(
                         @PathVariable String videoId){
        vodService.deleteVideo(videoId);
        System.out.println("视频删除成功--"+videoId);
        return ResponseResult.ok().message("视频删除成功");
    }


    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public ResponseResult getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    AliyunVodSDKUtils.initVodClient(VodConstant.ACCESS_KEY_ID,
                            VodConstant.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return ResponseResult.ok().data("playAuth",playAuth);
        }catch(Exception e) {
            System.out.println(e);
            throw new FmException(20001,"获取凭证失败");
        }
    }

    @GetMapping("testVod/{id}")
    public ResponseResult test(@PathVariable String id){
        return ResponseResult.ok().message("testVod-请求成功"+id);
    }

}




