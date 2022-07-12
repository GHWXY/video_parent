package com.wxy.services_user.controller;

import com.alibaba.fastjson.JSON;
import com.wxy.services_user.entity.User;
import com.wxy.services_user.service.UserService;
import com.wxy.services_user.utils.HttpClientUtil;
import com.wxy.services_user.utils.WxConstantUtils;
import com.wxy.utils.JwtUtils;
import com.wxy.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/service_user/user/wx")
public class WxController {
    @Autowired
    private UserService userService;
    //记录当前是否已经登录
    private boolean isLogin;
    //记录当前是否扫码登录失败
    private boolean isLoginFail;
    //当前登录用户token
    private String jwtToken;

    @GetMapping("/checkLogin")
    public ResponseResult checkLogin() {
        ResponseResult result = null;
        int flag = 1;
        while (true) {
            if (isLogin) { //如果登录状态已经,登录,返回用户token
                result = ResponseResult.ok().data("token", this.jwtToken);
                isLogin = false;//状态复位
                break;//退出
            }
            if (isLoginFail) {//扫描登录失败 返回错误信息
                result = ResponseResult.error();
            }
            try { //每500毫秒查询一下状态
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (flag > 500) { //超时处理
                result = ResponseResult.error();
                break;
            }
            flag++;
        }
        return result;
    }

    @RequestMapping("/callBack")
    public void callback(String code, String state) {
        //如果一致则放行，如果不一致则抛出异常：非法访问
        try {
            //需修改为自己的app环境
            String getToken = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                    "appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接请求地址
            getToken = String.format(getToken,
                    WxConstantUtils.WX_OPEN_APP_ID,
                    WxConstantUtils.WX_OPEN_APP_SECRET,
                    code);
            //回调获得code，通过用户授权的code去获取微信令牌
            String token = HttpClientUtil.get(getToken);
            Map map = JSON.parseObject(token);
            //获取到了关键的令牌和openid后，
            //就可以正式开始查询微信用户的信息，完成我们要做的微信绑定
            String access_token = (String) map.get("access_token");
            String openid = (String) map.get("openid");
            //查询数据库当中有没有该用户
            User user = userService.getByOpenid(openid);
            if (user == null) { //如果没有用户
                String userInfo = "" + "https://api.weixin.qq.com/sns/userinfo?" +
                        "access_token=%s" +
                        "&openid=%s";
                userInfo = String.format(userInfo, access_token, openid);
                //获取微信用户信息
                String wxUserInfo = HttpClientUtil.get(userInfo);
                //解析用户json信息
                Map info = JSON.parseObject(wxUserInfo);
                //获取昵称与头像
                String nickname = (String) info.get("nickname");
                String headimgurl = (String) info.get("headimgurl");
                //向数据库中插入一条记录
                user = new User();
                user.setNickname(nickname);
                user.setOpenid(openid);
                user.setAvatar(headimgurl);
                //保存用户信息
                userService.save(user);
            }
            //使用jwt根据user对象生成token字符串
            jwtToken = JwtUtils.getJwtToken(user.getId(), user.getNickname());
            this.isLogin = true;
        } catch (Exception e) {
            this.isLoginFail = true;
        }
    }


}
