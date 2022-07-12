package com.wxy.services_pay.controller;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.wxy.services_pay.common.properties.AliPayProperties;
import com.wxy.services_pay.entity.PayOrder;
import com.wxy.services_pay.service.PayLogService;
import com.wxy.services_pay.service.PayOrderService;
import com.wxy.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/callback")
public class AliPayCallBackUrl {
    private static final String CHARSET = "UTF-8";
    @Autowired
    private PayLogService payLogService;
    @Autowired
    private PayOrderService payOrderService;
    @Autowired
    private AliPayProperties aliPayProperties;

    /*** 只有付款成功才会跳转到这里且只跳转一次*/
    @GetMapping("/returnUrl")
    public String returnUrl(){
        try {
            HttpServletResponse response = ServletUtils.getResponse();
            HttpServletRequest request = ServletUtils.getRequest();
            response.setContentType("text/html;charset=" + CHARSET);
            PrintWriter out = response.getWriter();
            //获取支付宝GET过来反馈信息
            boolean signVerified = checkV1(request);
            //——请在这里编写您的程序（以下代码仅作参考）——
            if (signVerified) {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no")
                        .getBytes("ISO-8859-1"), "UTF-8");
                PayOrder payOrder = payOrderService.getOrderByOrderNo(out_trade_no);
                String contentId = payOrder.getContentId();
                String url = "http://localhost:3000/video/"+contentId;
                return "redirect:"+url;
            } else {
                out.println("验签回调失败");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 服务器通知 :
     * 1. ip地址一定是公网的，私有地址支付宝无法通知到客户端
     * 2. 请求方式是POST请求
     * @return
     */
    @PostMapping("/notifyUrl")
    public void notifyUrl(){
        HttpServletResponse response = ServletUtils.getResponse();
        HttpServletRequest request = ServletUtils.getRequest();
        try {
            PrintWriter out = response.getWriter();
            boolean signVerified = checkV1(request);
            System.out.println(signVerified);
            if (signVerified) {//验证成功
                System.out.println("异步验证成功");
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no")
                        .getBytes("ISO-8859-1"), "UTF-8");
                //支付宝交易号
                String transaction_id = new String(request.getParameter("trade_no")
                        .getBytes("ISO-8859-1"), "UTF-8");
                //交易状态
                String trade_status = new String(request.getParameter("trade_status")
                        .getBytes("ISO-8859-1"), "UTF-8");
                HashMap<String, String> map = new HashMap<>();
                map.put("out_trade_no",out_trade_no);
                map.put("trade_state",trade_status);
                map.put("transaction_id",transaction_id);
                if (trade_status.equals("TRADE_FINISHED")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，
                    // 并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                    System.out.println("订单已完成");
                    PayOrder payOrder = payOrderService.getOrderByOrderNo(out_trade_no);
                    if (payOrder.getStatus().intValue() != 1){
                        payLogService.updateOrdersStatus(map);
                    }
                } else if (trade_status.equals("TRADE_SUCCESS")) {
                    //判断该笔订单是否在商户网站中已经做过处理
                    //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，
                    // 并执行商户的业务程序
                    //如果有做过处理，不执行商户的业务程序
                    //我们的业务代码
                    System.out.println("订单付款成功");
                    payLogService.updateOrdersStatus(map);
                }
                out.println("success");
            } else {//验证失败
                out.println("fail");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    /*** 校验签名*/
    private boolean checkV1(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            /* valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");*/
            params.put(name, valueStr);
        }
        return AlipaySignature.rsaCheckV1(
                params,
                aliPayProperties.getPublvicKey(),
                "UTF-8",
                "RSA2"
        );
    }
}
