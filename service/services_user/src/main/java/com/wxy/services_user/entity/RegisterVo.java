package com.wxy.services_user.entity;

import lombok.Data;

@Data
public class RegisterVo {
    /**昵称*/
    private String nickname;
    /**手机号*/
    private String phone;
    /**密码*/
    private String password;
    /**验证码*/
    private String code;
}
