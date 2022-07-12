package com.wxy.services_pay.controller;

import com.wxy.services_pay.service.PayLogService;
import com.wxy.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service_pay/pay-log")
public class PayLogController {
    @Autowired
    private PayLogService payService;
    @GetMapping("/aliPay/{orderNo}")
    public ResponseResult aliPay(@PathVariable String orderNo) {
        String form = payService.aliPay(orderNo);
        return ResponseResult.ok().data("form",form);
    }
}



