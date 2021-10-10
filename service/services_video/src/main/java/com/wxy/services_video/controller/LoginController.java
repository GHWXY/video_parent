package com.wxy.services_video.controller;

import com.wxy.utils.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author wxy
 * @date 2021年08月31日 17:39
 */
@RestController
@RequestMapping("/services_video/user")
@CrossOrigin
public class LoginController {
    @PostMapping("/login")
    public ResponseResult login(){
        return ResponseResult.ok().data("token","admin-token");
    }

    @GetMapping("/info")
    public ResponseResult info(){
        return   ResponseResult.ok()
                .data("roles","[admin]")
                .data("name","wxy")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }



}
