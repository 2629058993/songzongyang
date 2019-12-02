package com.itzy.controller;

import com.itzy.biz.Brand.IBrandService;
import com.itzzy.commons.Datableresult;
import com.itzzy.commons.ServerResponse;
import com.itzzy.po.Brand;
import com.itzzy.pram.BrandPram;
import com.itzzy.util.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("Brand")
@CrossOrigin(maxAge = 3600, origins = "http://localhost:8083")
public class BrandController {

    @Resource
    private IBrandService brandservice;

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("brandlist")
    @ResponseBody
    public ServerResponse brandlist(BrandPram brandpram) {
        Datableresult datableresult = brandservice.brandlist(brandpram);
        return ServerResponse.success(datableresult);
    }

    @RequestMapping("tolist")
    public String tolist() {
        return "Brand/list";
    }

    @PostMapping("findbytypeid")
    @ResponseBody
    public ServerResponse findbytypeid(long fatherid) {
        boolean b = redisUtil.hasKey(fatherid + "brand");
        if (b) {
            List<Brand> brandListt = (List<Brand>) redisUtil.get(fatherid + "brand");
            return ServerResponse.success(brandListt);
        } else {
            List<Brand> brandList = brandservice.findbytypeid(fatherid);
            redisUtil.set(fatherid + "brand", brandList);
            return ServerResponse.success(brandList);
        }

    }
}
