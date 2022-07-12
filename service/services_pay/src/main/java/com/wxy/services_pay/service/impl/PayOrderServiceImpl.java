package com.wxy.services_pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxy.services_pay.client.ContentClient;
import com.wxy.services_pay.client.UserClient;
import com.wxy.services_pay.entity.PayOrder;
import com.wxy.services_pay.mapper.PayOrderMapper;
import com.wxy.services_pay.service.PayOrderService;
import com.wxy.utils.UUIDUtils;
import entity.ContentWebVO;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements PayOrderService {
    @Autowired
    private ContentClient contentClient;
    @Autowired
    private UserClient userClient;

    @Override
    public String saveOrder(String contentId, String userId) {
        //通过远程调用根据用户id获取用户信息
        User userInfoOrder = userClient.getUserInfoOrderById(userId);
        //通过远程调用根据作品id获取作品信息
        ContentWebVO contentWebVO = contentClient.getContentInfoOrder(contentId);
        //创建Order对象，向order对象里面设置需要数据
        PayOrder order = new PayOrder();
        order.setOrderNo(UUIDUtils.getRandomNumber(16));//订单号
        order.setContentId(contentId); //课程id
        order.setContentTitle(contentWebVO.getTitle());
        order.setContentCover(contentWebVO.getCover());
        order.setAuthorName(contentWebVO.getAuthorName());
        order.setTotalFee(contentWebVO.getPrice());
        order.setUserId(userId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);  //订单状态（0：未支付 1：已支付）
        order.setPayType(2);  //支付类型 ，支付宝2
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();
    }
    @Override
    public PayOrder getOrderByOrderNo(String OrderNo) {
        QueryWrapper<PayOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",OrderNo);
        PayOrder order = this.getOne(queryWrapper);
        return order;
    }


}
