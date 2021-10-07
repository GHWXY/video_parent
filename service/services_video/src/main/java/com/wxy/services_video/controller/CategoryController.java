package com.wxy.services_video.controller;


import com.wxy.services_video.entity.category.LevelCategory;
import com.wxy.services_video.service.CategoryService;
import com.wxy.utils.ResponseResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 科目分类 前端控制器
 * </p>
 *
 * @author wxy
 * @since 2021-09-02
 */
@RestController
@RequestMapping("/services_video/category")
/*@CrossOrigin*/
public class CategoryController {
    @Resource
    private CategoryService categoryService;

    /**
     * excel分类文件上传
     * @param file
     * @return
     */
    @PostMapping("/addCategory")
    public ResponseResult addCategory(MultipartFile file){
        //调用业务上传Excel
        categoryService.addCategory(file);
       return ResponseResult.ok();
    }

    /**
     * 获取分类列表(树形结构)
     * @return
     */
    @GetMapping("/getAllCategory")
    public ResponseResult getAllCategory(){
        List<LevelCategory> list =  categoryService.getTreeCategory();
        return ResponseResult.ok().data("list",list);
    }

}

