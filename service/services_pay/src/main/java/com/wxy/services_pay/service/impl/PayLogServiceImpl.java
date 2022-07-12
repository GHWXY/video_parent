package com.wxy.services_pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxy.services_base.exception.FmException;
import com.wxy.services_pay.common.config.AliPayApiConfig;
import com.wxy.services_pay.common.properties.AliPayProperties;
import com.wxy.services_pay.entity.PayLog;
import com.wxy.services_pay.entity.PayOrder;
import com.wxy.services_pay.mapper.PayLogMapper;
import com.wxy.services_pay.service.PayLogService;
import com.wxy.services_pay.service.PayOrderService;
import com.wxy.utils.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;


@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String CHARSET = "UTF-8";//编码
    private static final String EXPIRE = "30m";//交易多长时间后关闭
    @Autowired
    private AliPayProperties aliPayProperties;
    @Autowired
    private AliPayApiConfig aliPayApiConfig;
    @Autowired
    private PayOrderService payOrderService;
    /**电脑网站支付宝支付*/
    @Override
    public String aliPay(String orderNo) {
        //1 根据订单号查询订单信息
        QueryWrapper<PayOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        PayOrder order = payOrderService.getOne(wrapper);
        //支付内容参数
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(order.getOrderNo());
        //销售产品码 电脑网站支付固定为 FAST_INSTANT_TRADE_PAY
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        //商品金额
        model.setTotalAmount(order.getTotalFee().toString());
        //商品标题
        model.setSubject(order.getContentTitle());
        //商品描述
        model.setBody("支付宝支付，共" + order.getTotalFee() + "元");
        //设置订单最晚付款时间  过期交易将关闭
        model.setTimeoutExpress(EXPIRE);
        //构建请求
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setReturnUrl(aliPayProperties.getReturnUrl());
        request.setNotifyUrl(aliPayProperties.getNotyfyUrl());
        request.setBizModel(model);
        //发起请求
        try {
            String form = aliPayApiConfig.getAliPayClient().pageExecute(request).getBody();
            HttpServletResponse response = ServletUtils.getResponse();
            response.setContentType("text/html;charset=" + CHARSET);
            return form;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            throw new FmException(20001,"支付宝电脑网站支付失败");
        }
    }


    @Override
    public void updateOrdersStatus(Map<String, String> map) {
        //从map获取订单号
        String orderNo = map.get("out_trade_no");
        //根据订单号查询订单信息
        QueryWrapper<PayOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        PayOrder order = payOrderService.getOne(wrapper);

        //更新订单表订单状态
        if(order.getStatus().intValue() == 1) { return; }
        order.setStatus(1);//1代表已经支付
        payOrderService.updateById(order);

        //向支付表添加支付记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);  //订单号
        payLog.setPayTime(new Date()); //订单完成时间
        payLog.setPayType(2);//支付类型 1微信
        payLog.setTotalFee(order.getTotalFee());//总金额(分)

        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id")); //流水号
        payLog.setAttr(JSONObject.toJSONString(map));

        baseMapper.insert(payLog);
    }

}
