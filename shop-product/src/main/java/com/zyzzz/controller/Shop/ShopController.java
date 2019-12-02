package com.zyzzz.controller.Shop;

import com.itzzy.po.Shop;
import com.zyzzz.biz.Shop.IShopService;
import com.itzzy.commons.Datableresult;
import com.itzzy.commons.ServerResponse;
import com.itzzy.pram.ShopPram;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("shop")
@CrossOrigin(origins = "http://localhost:8083", maxAge = 3600)
public class ShopController {
    @Resource
    private IShopService shopservice;

    @RequestMapping("tolist")
    public String tolist() {
        return "Shop/list";
    }

    @PostMapping("shoplist")
    @ResponseBody
    public ServerResponse shoplist(ShopPram shoppram) {
        Datableresult datableresult = shopservice.shoplist(shoppram);
        return ServerResponse.success(datableresult);
    }


    @RequestMapping("testspring")
    @ResponseBody
    public ServerResponse testspring() {
        shopservice.testspring();
        return ServerResponse.success();
    }

    @RequestMapping("findshopbyid/{productid}")
    @ResponseBody
    public Shop addcart(@PathVariable Integer productid) {
        Shop shop = shopservice.findshopbyid(productid);
        return shop;
    }
}
