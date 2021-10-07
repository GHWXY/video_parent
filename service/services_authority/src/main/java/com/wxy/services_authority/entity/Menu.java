package com.wxy.services_authority.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单权限
 * </p>
 *
 * @author wxy
 * @since 2021-10-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 所属上级
     */
    private String pid;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型(1:菜单,2:按钮)
     */
    private Integer type;

    /**
     * 权限值
     */
    private String permissionValue;

    /**
     * 访问路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 图标
     */
    private String icon;

    /**
     * 状态(0:禁止,1:正常)
     */
    private Integer status;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    private Boolean isDeleted;


}
