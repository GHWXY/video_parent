package com.wxy.services_authority.service;

import com.wxy.services_authority.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author wxy
 * @since 2021-10-07
 */
public interface RoleService extends IService<Role> {
    /**
     * 根据用户查询角色
     * @param userId
     * @return
     */
    Map<String, Object> findRoleByUserId(String userId);

    /**
     * 根据用户分配角色
     * @param userId
     * @param roleId
     */
    void saveUserRoleRelationShip(String userId, String[] roleId);
}
