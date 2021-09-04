package com.wxy.services_video.entity.vo;

import lombok.Data;

@Data
public class ContentPreviewVO {
    private String title;
    private String cover;
    private Integer contentNum;
    private String oneCategory;
    private String twoCategory;
    private String author;
    private String price;//只用于显示
}
