package com.wxy.services_pay.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxy.services_pay.entity.PayOrder;
import com.wxy.services_pay.service.PayOrderService;
import com.wxy.utils.JwtUtils;
import com.wxy.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/service_pay/pay-order")
public class PayOrderController {
    @Autowired
    private PayOrderService orderService;
    //根据作品id和用户id创建订单，返回订单id
    @PostMapping("createOrder/{contentId}")
    public ResponseResult save(@PathVariable String contentId, HttpServletRequest request) {
        String uid = JwtUtils.getUserIdByJwtToken(request);
        System.out.println("uid = "+ uid);
        String orderId = orderService.saveOrder(contentId, JwtUtils.getUserIdByJwtToken(request));
        return ResponseResult.ok().data("orderId", orderId);
    }

    //2 根据订单id查询订单信息
    @GetMapping("/getOrderInfo/{orderNo}")
    public ResponseResult getOrderInfo(@PathVariable String orderNo) {
        PayOrder order = orderService.getOrderByOrderNo(orderNo);
        return ResponseResult.ok().data("item",order);
    }


    //3.根据用户id和作品id查询该用户是否已经购买该作品
    @GetMapping("/isBuyContent/{userid}/{id}")
    public boolean isBuyContent(@PathVariable String userid,
                               @PathVariable String id) {
        //订单状态是1表示支付成功
        int count = orderService.count(new QueryWrapper<PayOrder>()
                .eq("user_id", userid)
                .eq("content_id", id)
                .eq("status", 1));
        if(count>0) {
            return true;
        } else {
            return false;
        }
    }

}

