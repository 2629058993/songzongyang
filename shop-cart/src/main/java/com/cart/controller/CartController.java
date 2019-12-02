package com.cart.controller;


import com.cart.biz.ICartService;
import com.itzzy.aspect.ShopAnnotation;
import com.itzzy.commons.ServerResponse;
import com.itzzy.po.Carts;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cart")
@CrossOrigin(maxAge = 3600, origins = "http://localhost:8083")
public class CartController {
    @Resource
    private ICartService icartservice;

    @PostMapping("addcarts")
    @ShopAnnotation
    public ServerResponse test(String productid) {
        icartservice.addcarts(productid);
        return ServerResponse.success();
    }

    @PostMapping("catcarts")
    @ShopAnnotation
    public ServerResponse catcarts() {
        Map<String, Object> cartsList = icartservice.catcarts();
        return ServerResponse.success(cartsList);
    }


    //前台增加
    @PostMapping("incrcart")
    @ShopAnnotation
    public ServerResponse incrcart(String shopid) {
        icartservice.incrcart(shopid);
        return ServerResponse.success();
    }


    //前台减少
    @PostMapping("reduce")
    @ShopAnnotation
    public ServerResponse reduce(String shopid) {
        icartservice.reduce(shopid);
        return ServerResponse.success();
    }


    //删
    @PostMapping("remove")
    @ShopAnnotation
    public ServerResponse remove(String shopid) {
        icartservice.remove(shopid);
        return ServerResponse.success();
    }

    //单点
    @PostMapping("uncheck")
    @ShopAnnotation
    public ServerResponse uncheck(String shopid) {
        icartservice.uncheck(shopid);
        return ServerResponse.success();
    }

    //全选全不选
    @PostMapping("isallcheck")
    @ShopAnnotation
    public ServerResponse isallcheck(String checkclass) {
        icartservice.isallcheck(checkclass);
        return ServerResponse.success();
    }


    //更新个数
    @PostMapping("updatecount")
    @ShopAnnotation
    public ServerResponse updatecount(Integer count,String shopid) {
        icartservice.updatecount(count,shopid);
        return ServerResponse.success();
    }

}
