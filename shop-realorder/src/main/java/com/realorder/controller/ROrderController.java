package com.realorder.controller;

import com.itzzy.aspect.ShopAnnotation;
import com.itzzy.commons.ServerResponse;
import com.realorder.biz.IROrderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("ROrder")
@CrossOrigin(maxAge = 3600, origins = "http://localhost:8083")
public class ROrderController {
    @Resource
    private IROrderService irorderservice;

    //提交订单
    @PostMapping("addrorder")
    @ShopAnnotation
    public ServerResponse addrorder(Long recipientsid) {
        ServerResponse addrorder = irorderservice.addrorder(recipientsid);
        return addrorder;
    }


    //生成二维码
    @PostMapping("initEWMA")
    @ShopAnnotation
    public ServerResponse initEWMA(String outTradeNo) {
        ServerResponse serverResponse = irorderservice.initEWMA(outTradeNo);
        return serverResponse;
    }


    //检测是否扫码支付
    @PostMapping("detectionisqr")
    @ShopAnnotation
    public ServerResponse detectionisqr(long orderid, String outTradeNo) {
        ServerResponse serverResponse = irorderservice.detectionisqr(orderid,outTradeNo);
        return serverResponse;
    }

}
