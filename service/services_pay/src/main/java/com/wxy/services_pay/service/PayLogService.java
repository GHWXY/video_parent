package com.wxy.services_pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.services_pay.entity.PayLog;

import java.util.Map;


public interface PayLogService extends IService<PayLog> {
    //阿里电脑网站支付
    String aliPay(String orderId);
    //更新订单状态
    void updateOrdersStatus(Map<String, String> map);
}

