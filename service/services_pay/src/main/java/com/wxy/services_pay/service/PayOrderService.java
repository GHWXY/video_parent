package com.wxy.services_pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxy.services_pay.entity.PayOrder;

public interface PayOrderService extends IService<PayOrder> {
    /*创建订单*/
    String saveOrder(String contentId, String userId);
    /*根据订单号查询订单*/
    PayOrder getOrderByOrderNo(String OrderNo);
}
