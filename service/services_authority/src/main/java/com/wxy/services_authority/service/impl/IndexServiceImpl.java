package com.wxy.services_authority.service.impl;

import com.alibaba.fastjson.JSONObject;


import com.wxy.services_authority.entity.Role;
import com.wxy.services_authority.entity.User;
import com.wxy.services_authority.service.IndexService;
import com.wxy.services_authority.service.MenuService;
import com.wxy.services_authority.service.RoleService;
import com.wxy.services_authority.service.UserService;
import com.wxy.services_base.exception.FmException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RedisTemplate redisTemplate;

    /*** 根据用户名获取用户登录信息*/
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.selectByUsername(username);
        if (null == user) {
            new FmException(200001,"没有该用户");
        }
        //根据用户id获取角色
        List<Role> roleList = roleService.selectRoleByUserId(user.getId());
        List<String> roleNameList = roleList.stream().map(
                item -> item.getRoleName()
        ).collect(Collectors.toList());
        if(roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }
        //根据用户id获取操作权限值
        List<String> permissionValueList = menuService.selectPermissionValueByUserId(user.getId());
        //把权限缓存到redis当中
        redisTemplate.opsForValue().set(username, permissionValueList);
        result.put("name", user.getUsername());
        result.put("avatar", "http://thirdqq.qlogo.cn/g?b=sdk&k=5GQIoAWp5NU2FZN3fofZRw&s=140&t=1555735675");
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }

    /**
     * 根据用户名获取动态菜单
     * @param username
     * @return
     */
    public List<JSONObject> getMenu(String username) {
        User user = userService.selectByUsername(username);
        //根据用户id获取用户菜单权限
        List<JSONObject> permissionList = menuService.selectPermissionByUserId(user.getId());
        return permissionList;
    }


}
