package com.wxy.sms.controller;

import com.wxy.sms.service.SmsService;
import com.wxy.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/service_sms/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @GetMapping(value = "/send/{phone}")
    public ResponseResult code(@PathVariable String phone) {
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)) {
            return ResponseResult.ok();
        }
        code = randomCode();
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);
        boolean isSend = smsService.send(phone, "SMS_169111508", param);
        if(isSend) {
            redisTemplate.opsForValue().set(phone, code,1, TimeUnit.MINUTES);
            return ResponseResult.ok();
        } else {
            return ResponseResult.error().message("发送短信失败");
        }
    }
    public String randomCode(){
        StringBuffer sb = new StringBuffer();
        for (int i =0; i < 4; i++) {
            int s  = new Random().nextInt(10);
            sb.append(s);
        }
        return sb.toString();
    }
}
