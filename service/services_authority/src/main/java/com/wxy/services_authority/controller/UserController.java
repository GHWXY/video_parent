package com.wxy.services_authority.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxy.services_authority.entity.User;
import com.wxy.services_authority.service.RoleService;
import com.wxy.services_authority.service.UserService;
import com.wxy.utils.MD5;
import com.wxy.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author wxy
 * @since 2021-10-07
 */
@RestController
@RequestMapping("/services_authority/admin/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    //获取管理用户分页列表
    @GetMapping("/getUserList/{page}/{limit}")
    public ResponseResult index(
            @PathVariable Long page,
            @PathVariable Long limit,
            User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }

        IPage<User> pageModel = userService.page(pageParam, wrapper);
        return ResponseResult.ok().data("items", pageModel.getRecords())
                .data("total", pageModel.getTotal());
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("/saveUser")
    public ResponseResult save(@RequestBody User user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        userService.save(user);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "修改管理用户")
    @PostMapping("/updateUser")
    public ResponseResult updateById(@RequestBody User user) {
        userService.updateById(user);
        return ResponseResult.ok();
    }


    @ApiOperation(value = "删除管理用户")
    @PostMapping("/removeUser/{id}")
    public ResponseResult remove(@PathVariable String id) {
        userService.removeById(id);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "批量删除用户")
    @PostMapping("/batchRemoveUser")
    public ResponseResult batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/getUserRoleData/{userId}")
    public ResponseResult toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return ResponseResult.ok().data(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssignRole")
    public ResponseResult doAssign(@RequestParam String userId,@RequestParam String[] roleId) {
        roleService.saveUserRoleRelationShip(userId,roleId);
        return ResponseResult.ok();
    }


    //根据用户id查询用户
    @GetMapping("/getUserById/{id}")
    public ResponseResult getUser(@PathVariable String id){
        User user = userService.getById(id);
        return ResponseResult.ok().data("user",user);
    }



}

