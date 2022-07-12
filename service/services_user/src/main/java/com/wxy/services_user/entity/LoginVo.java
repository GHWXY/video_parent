package com.wxy.services_user.entity;

import lombok.Data;

@Data
public class LoginVo {
    /**手机号*/
    private String phone;
    /**密码*/
    private String password;
}

