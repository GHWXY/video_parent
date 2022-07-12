package entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContentWebVO {
    private String id;
    //作品标题
    private String title;
    //销售价格
    private BigDecimal price;
    //合辑视频图
    private Integer contentNum;
    //封面图片路径
    private String cover;
    //销售数量
    private Long buyCount;
    //浏览数量
    private Long viewCount;
    //作品简介
    private String description;
    //作者ID
    private String authorId;
    //作者姓名
    private String authorName;
    //作者简介
    private String intro;
    //作者头像
    private String avatar;
    //作品一级分类ID
    private String oneCategoryId;
    //一级类别名称
    private String oneCategoryName;
    //二级分类ID
    private String twoCategoryId;
    //二级分类名称
    private String twoCategoryName;
}
