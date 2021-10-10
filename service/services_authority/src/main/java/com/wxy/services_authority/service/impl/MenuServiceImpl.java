package com.wxy.services_authority.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxy.services_authority.entity.Menu;
import com.wxy.services_authority.entity.RoleMenu;
import com.wxy.services_authority.entity.User;
import com.wxy.services_authority.helper.MenuHelper;
import com.wxy.services_authority.helper.PermissionHelper;
import com.wxy.services_authority.mapper.MenuMapper;
import com.wxy.services_authority.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxy.services_authority.service.RoleMenuService;
import com.wxy.services_authority.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单权限 服务实现类
 * </p>
 *
 * @author wxy
 * @since 2021-10-07
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private UserService userService;
    //获取全部菜单
    @Override
    public List<Menu> queryAllMenu() {

        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Menu> permissionList = baseMapper.selectList(wrapper);

        List<Menu> result = bulid(permissionList);

        return result;
    }
    /**
     * 使用递归方法建菜单
     * @param treeNodes
     * @return
     */
    private static List<Menu> bulid(List<Menu> treeNodes) {
        List<Menu> trees = new ArrayList<>();
        for (Menu treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    private static Menu findChildren(Menu treeNode,List<Menu> treeNodes) {
        treeNode.setChildren(new ArrayList<Menu>());

        for (Menu it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }

    //递归删除菜单
    @Override
    public void removeChildById(String id) {
        List<String> idList = new ArrayList<>();
        this.selectChildListById(id, idList);
        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    /**
     *	递归获取子节点
     * @param id
     * @param idList
     */
    private void selectChildListById(String id, List<String> idList) {
        List<Menu> childList = baseMapper.selectList(
                new QueryWrapper<Menu>().eq("pid", id).select("id")
        );
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }

    //给角色分配权限
    @Override
    public void saveRoleMenuRelationShip(String roleId, String[] menuIds) {

        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id", roleId));

        List<RoleMenu> rolePermissionList = new ArrayList<>();
        for(String permissionId : menuIds) {
            if(StringUtils.isEmpty(permissionId)) continue;
            RoleMenu rolePermission = new RoleMenu();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        roleMenuService.saveBatch(rolePermissionList);
    }

    //根据角色获取菜单
    @Override
    public List<Menu> selectAllMenu(String roleId) {
        List<Menu> allMenuList = baseMapper.selectList(
                new QueryWrapper<Menu>().orderByAsc("CAST(id AS SIGNED)"));
        //根据角色id获取角色权限
        List<RoleMenu> rolePermissionList = roleMenuService.list(
                new QueryWrapper<RoleMenu>().eq("role_id",roleId));

        for (int i = 0; i < allMenuList.size(); i++) {
            Menu permission = allMenuList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RoleMenu roleMenu = rolePermissionList.get(m);
                if(roleMenu.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }
        List<Menu> permissionList = bulid(allMenuList);
        return permissionList;
    }

    /**
     * 根据用户id获取用户菜单
     * @param id
     * @return
     */
    @Override
    public List<String> selectPermissionValueByUserId(String id) {
        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllMenuValue();
        } else {//根据用户查询菜单权限
            selectPermissionValueList = baseMapper.selectMenuValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Menu> selectMenuList = null;
        if(this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectMenuList = baseMapper.selectList(null);
        } else {
            selectMenuList = baseMapper.selectMenuByUserId(userId);
        }

        List<Menu> permissionList = PermissionHelper.bulid(selectMenuList);
        List<JSONObject> result = MenuHelper.build(permissionList);
        return result;
    }

    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

}
