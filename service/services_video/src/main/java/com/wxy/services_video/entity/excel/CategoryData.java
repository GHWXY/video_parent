package com.wxy.services_video.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CategoryData {
    @ExcelProperty(index = 0)
    private String oneCategoryData;
    @ExcelProperty(index = 1)
    private String twoCategoryData;
}
