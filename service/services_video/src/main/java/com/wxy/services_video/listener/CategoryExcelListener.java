package com.wxy.services_video.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxy.services_video.entity.Category;
import com.wxy.services_video.entity.excel.CategoryData;
import com.wxy.services_video.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wxy
 * @date 2021年09月02日 23:54
 */
@Component
public class CategoryExcelListener extends AnalysisEventListener<CategoryData> {
  @Autowired
  private CategoryService categoryService;

  /**
   * 每读取一行执行此方法（注：只有一行sheet只有一行时不执行，直接执行doAfterAllAnalysed）
   * @param categoryData
   * @param analysisContext
   */
  @Override
  public void invoke(CategoryData categoryData, AnalysisContext analysisContext) {
    System.out.println(categoryData);
    //写入到数据库
    if (categoryData != null){
      //如果一级分类不存在,就保存到数据库当中
      Category oneCategory = this.isExistOneCategory(categoryData);
      if (oneCategory == null){
        oneCategory = new Category();
        oneCategory.setTitle(categoryData.getOneCategoryData());
        oneCategory.setParentId("0");
        categoryService.save(oneCategory);
      }
      //保存2级分类  先判断2级分类是否已经存在
      Category twoCategory = this.isExistTwoCategory(categoryData, oneCategory.getId());
      if (twoCategory == null){
        twoCategory = new Category();
        twoCategory.setTitle(categoryData.getTwoCategoryData());
        twoCategory.setParentId(oneCategory.getId());
        categoryService.save(twoCategory);
      }

    }

  }

  /**
   * 1.判断1级分类是否已经存在
   * @param categoryData
   * @return
   */
  private Category isExistOneCategory(CategoryData categoryData){
    QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("title",categoryData.getOneCategoryData());
    queryWrapper.eq("parent_id","0");
    Category category = categoryService.getOne(queryWrapper);
    return category;
  }

  /**
   * 2.判断2级分类是否已经存在
   * @param categoryData
   * @param pid
   * @return
   */
  private Category isExistTwoCategory(CategoryData categoryData,String pid){
    QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("title",categoryData.getTwoCategoryData());
    queryWrapper.eq("parent_id",pid);
    Category category = categoryService.getOne(queryWrapper);
    return category;
  }

  /**
   * 全部读取完之后执行此方法
   * @param analysisContext
   */
  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    System.out.println(analysisContext);
  }
}
