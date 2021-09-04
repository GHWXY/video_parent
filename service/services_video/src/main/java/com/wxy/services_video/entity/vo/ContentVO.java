package com.wxy.services_video.entity.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContentVO {
    private String id;
    private String title;//标题
    private String categoryId; //二级分类ID
    private String categoryParentId; //一级分类ID
    private String authorId;  //作者id
    private Integer contentNum; //总视频数
    private String description;//简介
    private String cover; //封面
    private BigDecimal price;
    //销量排序
    private String buyCountSort;
    //最新时间排序
    private String gmtCreateSort;
    //价格排序
    private String priceSort;
}
