package com.wxy.services_authority.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxy.services_authority.entity.Role;
import com.wxy.services_authority.service.RoleService;
import com.wxy.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色 前端控制器
 * </p>
 *
 * @author wxy
 * @since 2021-10-07
 */
@RestController
@RequestMapping("/services_authority/admin/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("/getRolePageList/{page}/{limit}")
    public ResponseResult index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            Role role) {
        Page<Role> pageParam = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name",role.getRoleName());
        }
        roleService.page(pageParam,wrapper);
        return ResponseResult.ok().data("items", pageParam.getRecords())
                .data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("/getRoleById/{id}")
    public ResponseResult get(@PathVariable String id) {
        Role role = roleService.getById(id);
        return ResponseResult.ok().data("item", role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("/saveRole")
    public ResponseResult save(@RequestBody Role role) {
        roleService.save(role);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "修改角色")
    @PostMapping("/updateRole")
    public ResponseResult updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "删除角色")
    @PostMapping("/removeRole/{id}")
    public ResponseResult remove(@PathVariable String id) {
        roleService.removeById(id);
        return ResponseResult.ok();
    }

    @ApiOperation(value = "根据id列表删除角色")
    @PostMapping("/batchRemoveRole")
    public ResponseResult batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return ResponseResult.ok();
    }









}

