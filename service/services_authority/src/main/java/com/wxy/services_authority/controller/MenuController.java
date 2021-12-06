package com.wxy.services_authority.controller;


import com.wxy.services_authority.entity.Menu;
import com.wxy.services_authority.service.MenuService;
import com.wxy.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单权限 前端控制器
 * </p>
 *
 * @author wxy
 * @since 2021-10-07
 */
@RestController
@RequestMapping("/services_authority/admin/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    /**查询所有菜单*/
    @GetMapping("/getAllMenu")
    public ResponseResult indexAllMenu() {
        List<Menu> list =  menuService.queryAllMenu();
        return ResponseResult.ok().data("children",list);
    }

    /**递归删除菜单*/
    @PostMapping("removeMenu/{id}")
    public ResponseResult remove(@PathVariable String id) {
        menuService.removeChildById(id);
        return ResponseResult.ok();
    }


    /**给角色分配权限*/
    @PostMapping("/doAssignRoleAuth")
    public ResponseResult doAssign(@RequestParam String roleId,@RequestParam String[] menus) {
        menuService.saveRoleMenuRelationShip(roleId,menus);
        return ResponseResult.ok();
    }

    /**根据角色获取菜单*/
    @GetMapping("/getMenuWithRoleId/{roleId}")
    public ResponseResult toAssign(@PathVariable String roleId) {
        List<Menu> list = menuService.selectAllMenu(roleId);
        return ResponseResult.ok().data("children", list);
    }

    /**新增菜单*/
    @PostMapping("/saveMenu")
    public ResponseResult save(@RequestBody Menu permission) {
        menuService.save(permission);
        return ResponseResult.ok();
    }

    /**修改菜单*/
    @PostMapping("/updateMenu")
    public ResponseResult updateById(@RequestBody Menu menu) {
        menuService.updateById(menu);
        return ResponseResult.ok();
    }

}

