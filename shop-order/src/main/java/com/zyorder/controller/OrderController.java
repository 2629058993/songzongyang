package com.zyorder.controller;

import com.itzzy.aspect.ShopAnnotation;
import com.itzzy.commons.ServerResponse;
import com.itzzy.po.OrderInfo;
import com.zyorder.biz.IOrderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("order")
@CrossOrigin(maxAge = 3600, origins = "http://localhost:8083")
public class OrderController {
    @Resource
    private IOrderService orderservice;


    @PostMapping("initaddress")
    @ShopAnnotation
    public ServerResponse initaddress() {
        List<OrderInfo> orderlist = orderservice.queryaddress();
        return ServerResponse.success(orderlist);
    }

    //切换默认收件人
    @PostMapping("checkrecipient")
    @ShopAnnotation
    public ServerResponse checkrecipient(Integer recipientid) {
        OrderInfo orderInfo = orderservice.checkrecipient(recipientid);
        return ServerResponse.success(orderInfo);
    }

    //增加收件人
    @PostMapping("addrecipients")
    @ShopAnnotation
    public ServerResponse addrecipients(OrderInfo orderInfo) {
        orderservice.addrecipients(orderInfo);
        return ServerResponse.success();
    }


    //回显数据Byid
    @PostMapping("echorecipients")
    @ShopAnnotation
    public ServerResponse echorecipients(Integer recipientsid) {
        OrderInfo orderInfo = orderservice.echorecipients(recipientsid);
        return ServerResponse.success(orderInfo);
    }


    //修改收件人
    @PostMapping("updaterecipients")
    @ShopAnnotation
    public ServerResponse updaterecipients(OrderInfo orderInfo) {
        orderservice.updaterecipients(orderInfo);
        return ServerResponse.success();
    }


    //删除收件人
    @PostMapping("deleterecipients")
    @ShopAnnotation
    public ServerResponse deleterecipients(Integer recipientsid) {
        orderservice.deleterecipients(recipientsid);
        return ServerResponse.success();
    }
}
