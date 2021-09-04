package com.wxy.services_video.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContentVideoInfoVO {
    /**
     * 视频ID
     */
    @ApiModelProperty(value = "视频ID")
    private String id;

    /**
     * 作品ID
     */
    @ApiModelProperty(value = "作品ID")
    private String contentId;

    /**
     * 章节ID
     */
    @ApiModelProperty(value = "章节ID")
    private String chapterId;

    /**
     * 节点名称
     */
    @ApiModelProperty(value = "节点名称")
    private String title;

    /**
     * 云端视频资源
     */
    @ApiModelProperty(value = "云端视频资源")
    private String videoSourceId;

    /**
     * 视频资源标题
     */
    @ApiModelProperty(value = "视频资源标题")
    private String videoOriginalName;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private Integer sort;

    /**
     * 是否可以试听：0收费 1免费
     */
    @ApiModelProperty(value = "是否可以试听：0收费 1免费")
    private Boolean isFree;


}
