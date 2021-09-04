package com.wxy.services_video.controller;


import com.wxy.services_video.entity.Chapter;
import com.wxy.services_video.entity.vo.ChapterVO;
import com.wxy.services_video.service.ChapterService;
import com.wxy.services_video.service.impl.ChapterServiceImpl;
import com.wxy.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 作品章节 前端控制器
 * </p>
 *
 * @author wxy
 * @since 2021-09-04
 */
@RestController
@RequestMapping("/services_video/chapter")
@Api(tags = "作品章节",value = "ChapterController")
@CrossOrigin
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    /**
     * 章节列表信息
     * @param contentId
     * @return
     */
    @ApiOperation(value = "章节列表信息")
    @GetMapping("/getChapterContentVideo/{contentId}")
    public ResponseResult getChapterSection(
            @ApiParam(name = "contentId",value = "章节id",readOnly = true)
            @PathVariable String contentId){
        List<ChapterVO> chapterVOList = chapterService.chapterService(contentId);
        return ResponseResult.ok().data("items",chapterVOList);
    }

    /**
     * 新增章节
     * @param chapter
     * @return
     */
    @ApiOperation(value = "新增章节")
    @PostMapping("/addChapter")
    public ResponseResult addChapter(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return ResponseResult.ok();
    }

    /**
     * 根据id查询章节
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询章节")
    @GetMapping("/getChapterById/{id}")
    public ResponseResult getChapterById(
            @ApiParam(name = "id",value = "章节id",readOnly = true)
            @PathVariable String id){
        Chapter chapter = chapterService.getById(id);
        return ResponseResult.ok().data("item",chapter);
    }

    /**
     * 根据id修改章节
     * @return
     */
    @ApiOperation(value = "根据id修改章节")
    @PostMapping("/updateChapter")
    public ResponseResult updateChapter(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return ResponseResult.ok();
    }

    /**
     * 根据id删除章节
     * @return
     */
    @ApiOperation(value = "根据id删除章节")
    @PostMapping("/deleteChapterById/{id}")
    public ResponseResult deleteChapterById(@ApiParam(name = "id",value = "章节id",readOnly = true)
                                                @PathVariable String id){
        boolean result = chapterService.deleteChapterById(id);
        return ResponseResult.ok();
    }

}

