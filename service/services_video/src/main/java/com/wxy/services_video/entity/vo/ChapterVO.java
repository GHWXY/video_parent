package com.wxy.services_video.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data  //章节
public class ChapterVO {
    String id;
    String title;
    //小节
    List<ContentVideoVO> children = new ArrayList<>();
}
