package com.wxy.services_user.controller;

import com.wxy.services_base.exception.FmException;
import com.wxy.services_user.entity.LoginVo;
import com.wxy.services_user.entity.RegisterVo;
import com.wxy.services_user.entity.User;
import com.wxy.services_user.service.UserService;
import com.wxy.utils.JwtUtils;
import com.wxy.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/service_user/user")
public class UserController {

    @Autowired
    private UserService userService;

   /**用户登录*/
    @PostMapping("/login")
    public ResponseResult login(@RequestBody LoginVo loginVo) {
        String token = userService.login(loginVo);
        return ResponseResult.ok().data("token", token);
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody RegisterVo registerVo){
        userService.register(registerVo);
        return ResponseResult.ok();
    }
    @GetMapping("/getLoginInfo")
    public ResponseResult getLoginInfo(HttpServletRequest request){
        try {
            String userId = JwtUtils.getUserIdByJwtToken(request);
            //查询数据库根据用户id获取用户信息
            User user = userService.getById(userId);
            return ResponseResult.ok().data("userInfo",user);
        }catch (Exception e){
            e.printStackTrace();
            throw new FmException(20001,"获取失败");
        }
    }


    //根据用户id获取用户信息
    @PostMapping("/getUserInfoOrderById/{id}")
    public User getUserInfoOrderById(@PathVariable String id){
        User user = userService.getById(id);
        return user;
    }

    @GetMapping("/getTest")
    public ResponseResult getTest(){
        return ResponseResult.ok();
    }

}



