package com.wxy.services_video.entity.vo;

import lombok.Data;

@Data  //小节视频VO
public class ContentVideoVO {
    private  String id;
    private String title;
    private Boolean isFree;
    private String videoSourceId;
    private Float duration;
}
