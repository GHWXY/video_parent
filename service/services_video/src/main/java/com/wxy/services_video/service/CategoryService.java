package com.wxy.services_video.service;

import com.wxy.services_video.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.services_video.entity.category.LevelCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 科目分类 服务类
 * </p>
 *
 * @author wxy
 * @since 2021-09-02
 */
public interface CategoryService extends IService<Category> {

    /**
     * Excel分类文件上传
     * @param file
     */
    void addCategory(MultipartFile file);

    /**
     * 获取分类列表(树形结构)
     * @return
     */
    List<LevelCategory> getTreeCategory();
}
