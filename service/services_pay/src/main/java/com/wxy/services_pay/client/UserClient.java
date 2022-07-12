package com.wxy.services_pay.client;


import entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name="service-user")
@Component
public interface UserClient {
    //根据用户id获取用户信息
    @PostMapping(value = "/service_user/user/getUserInfoOrderById/{id}")
    User getUserInfoOrderById(@PathVariable("id") String id);
}

