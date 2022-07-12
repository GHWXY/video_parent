package com.wxy.services_user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.services_user.entity.LoginVo;
import com.wxy.services_user.entity.RegisterVo;
import com.wxy.services_user.entity.User;


public interface UserService extends IService<User> {
    /**登录*/
    String login(LoginVo loginVo);
    /**注册*/
    void register(RegisterVo registerVo);

    /**根据Openid获取用户*/
    User getByOpenid(String openid);
}
