package com.wxy.services_cms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 首页banner表
 * </p>
 *
 * @author wxy
 * @since 2022-01-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cms_banner")
public class Banner implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 链接地址
     */
    private String linkUrl;

    /**
     * 背景颜色
     */
    private String imageBg;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    private Boolean isDeleted;


}
