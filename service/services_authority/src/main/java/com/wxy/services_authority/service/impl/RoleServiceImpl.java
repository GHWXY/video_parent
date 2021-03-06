package com.wxy.services_authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxy.services_authority.entity.Role;
import com.wxy.services_authority.entity.UserRole;
import com.wxy.services_authority.mapper.RoleMapper;
import com.wxy.services_authority.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxy.services_authority.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author wxy
 * @since 2021-10-07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;
    //根据用户获取角色数据
    @Override
    public Map<String, Object> findRoleByUserId(String userId) {
        //查询所有的角色
        List<Role> allRolesList =baseMapper.selectList(null);
        //根据用户id，查询用户拥有的角色id
        List<UserRole> existUserRoleList = userRoleService.list(
                new QueryWrapper<UserRole>().eq("user_id", userId).select("role_id")
        );
        List<String> existRoleList = existUserRoleList.stream().map(
                c->c.getRoleId()).collect(Collectors.toList()
        );
        //对角色进行分类
        List<Role> assignRoles = new ArrayList<Role>();
        for (Role role : allRolesList) {
            //已分配
            if(existRoleList.contains(role.getId())) {
                assignRoles.add(role);
            }
        }
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignRoles);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }

    //根据用户分配角色
    @Override
    public void saveUserRoleRelationShip(String userId, String[] roleIds) {
        //删除原来关系
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));
        //重新建立关系
        List<UserRole> userRoleList = new ArrayList<>();
        for(String roleId : roleIds) {
            if(StringUtils.isEmpty(roleId)) continue;
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleList.add(userRole);
        }
        userRoleService.saveBatch(userRoleList);
    }

    @Override
    public List<Role> selectRoleByUserId(String id) {
        //根据用户id查询拥有的角色id
        List<UserRole> userRoleList = userRoleService
                .list(new QueryWrapper<UserRole>()
                        .eq("user_id", id)
                        .select("role_id"));
        //获取所有角色id
        List<String> roleIdList = userRoleList.stream()
                .map(item -> item.getRoleId())
                .collect(Collectors.toList());
        //根据角色id查询所有的角色信息
        List<Role> roleList = new ArrayList<>();
        if(roleIdList.size() > 0) {
            roleList = baseMapper.selectBatchIds(roleIdList);
        }
        return roleList;
    }
}
