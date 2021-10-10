package com.wxy.services_authority.controller;

import com.alibaba.fastjson.JSONObject;
import com.wxy.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wxy.services_authority.service.IndexService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/services_authority/admin/index")
public class IndexController {

    @Autowired
    private IndexService indexService;
    //根据token获取用户信息
    @GetMapping("info")
    public ResponseResult info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return ResponseResult.ok().data(userInfo);
    }

    //获取菜单
    @GetMapping("menu")
    public ResponseResult getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> menuList = indexService.getMenu(username);
        return ResponseResult.ok().data("menuList", menuList);
    }

    @PostMapping("logout")
    public ResponseResult logout(){
        return ResponseResult.ok();
    }
}
