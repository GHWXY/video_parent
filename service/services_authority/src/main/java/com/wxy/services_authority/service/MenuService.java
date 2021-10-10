package com.wxy.services_authority.service;

import com.alibaba.fastjson.JSONObject;
import com.wxy.services_authority.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单权限 服务类
 * </p>
 *
 * @author wxy
 * @since 2021-10-07
 */
public interface MenuService extends IService<Menu> {
    /**
     * 获取全部菜单
     * @return
     */
    List<Menu> queryAllMenu();

    /**
     * 递归删除菜单
     * @param id
     */
    void removeChildById(String id);

    /**
     * 给角色分配权限
     * @param roleId
     * @param split
     */
    void saveRoleMenuRelationShip(String roleId, String[] split);

    /**
     * 根据角色获取菜单
     * @param roleId
     * @return
     */
    List<Menu> selectAllMenu(String roleId);

    /**
     * 根据用户获取菜单
     * @param id
     * @return
     */
    List<String> selectPermissionValueByUserId(String id);

    /**
     * 根据用户id获取用户菜单权限
     * @param id
     * @return
     */
    List<JSONObject> selectPermissionByUserId(String id);
}
