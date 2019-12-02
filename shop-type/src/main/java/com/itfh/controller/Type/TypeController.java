package com.itfh.controller.Type;

import com.itfh.biz.Type.ITypeService;
import com.itzzy.commons.Datableresult;
import com.itzzy.commons.ServerResponse;
import com.itzzy.po.Type;
import com.itzzy.pram.TypePram;
import com.itzzy.util.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(maxAge = 3600, origins = "http://localhost:8083")
@Controller
@RequestMapping("Type")
public class TypeController {
    @Resource
    private ITypeService typeservice;

    @Resource
    private RedisUtil redisUtil;


    @RequestMapping("typeList")
    @ResponseBody

    public ServerResponse TypeList(TypePram typepram) {
        Datableresult datableresult = typeservice.typeList(typepram);
        return ServerResponse.success(datableresult);
    }

    @RequestMapping("tolist")
    public String tolist() {
        return "Type/list";
    }

    @RequestMapping("typelist")
    @ResponseBody
    public ServerResponse typelist(@RequestHeader(required = false) String token) {
        List<String> typeList = new ArrayList<>();
        typeList.add("哈哈哈哈哈");
        typeList.add("卡屯一诺卡机子" + token);
        return ServerResponse.success(typeList);
    }

    @PostMapping("jointbytype")
    @ResponseBody
    public ServerResponse jointbytype() {
        boolean typelist = redisUtil.hasKey("typelist");
        if (typelist) {
            List<Type> typelist1 = (List<Type>) redisUtil.get("typelist");
            return ServerResponse.success(typelist1);
        } else {
            List<Type> typeList = typeservice.jointbytype();
            redisUtil.set("typelist",typeList);
            return ServerResponse.success(typeList);
        }

    }
}
