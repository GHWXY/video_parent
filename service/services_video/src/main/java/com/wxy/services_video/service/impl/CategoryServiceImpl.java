package com.wxy.services_video.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxy.services_video.entity.Category;
import com.wxy.services_video.entity.category.LevelCategory;
import com.wxy.services_video.entity.excel.CategoryData;
import com.wxy.services_video.listener.CategoryExcelListener;
import com.wxy.services_video.mapper.CategoryMapper;
import com.wxy.services_video.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 科目分类 服务实现类
 * </p>
 *
 * @author wxy
 * @since 2021-09-02
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private CategoryExcelListener categoryExcelListener;
    @Override
    public void addCategory(MultipartFile file) {
        try {
            //读取Excel
            EasyExcel.read(file.getInputStream(), CategoryData.class,categoryExcelListener).sheet().doRead();
            //去监听器中保存数据--------CategoryExcelListener
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<LevelCategory> getTreeCategory() {
        //读取一级分类
        QueryWrapper<Category> oneQW = new QueryWrapper<>();
        oneQW.eq("parent_id",0);
        List<Category> oneCategoryList = baseMapper.selectList(oneQW);
        //读取二级分类
        QueryWrapper<Category> twoQW = new QueryWrapper<>();
        twoQW.ne("parent_id",0);
        List<Category> twoCategoryList = baseMapper.selectList(twoQW);
        //封装模型数据
        List<LevelCategory> resList = new ArrayList<>();
        for (Category oneCategory : oneCategoryList) {
            //把Category转成LevelCategory
            LevelCategory oneLevelCategory = new LevelCategory();
            //属性的复制
            //把前一个对象当中的属性值复制到后一个对象当中相同的属性中去
            //如果前一个对象在后一个对象当中没有找到相同的属性,就不复制
            BeanUtils.copyProperties(oneCategory,oneLevelCategory);

            //获取当前子分类有哪些
            //遍历所有的二级分类
            for (Category twoCategory : twoCategoryList) {
                //二级分类的parent_id=一级分类的id  就是当前一级分类 的子级
                if (twoCategory.getParentId().equals(oneCategory.getId())){
                    LevelCategory twoLevelCategory = new LevelCategory();
                    BeanUtils.copyProperties(twoCategory,twoLevelCategory);
                    oneLevelCategory.getChildren().add(twoLevelCategory);
                }
            }
            resList.add(oneLevelCategory);
        }

        return resList;
    }
}
