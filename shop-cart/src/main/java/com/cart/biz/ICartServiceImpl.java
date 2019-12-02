package com.cart.biz;

import com.alibaba.fastjson.JSONObject;
import com.cart.mapper.CartMapper;
import com.itzzy.po.Carts;
import com.itzzy.util.HttpRequestUtil;
import com.itzzy.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ICartServiceImpl implements ICartService {
    @Resource
    private CartMapper cartmapper;

    @Resource
    private HttpServletRequest request;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public void addcarts(String productid) {
        Integer userid = (Integer) request.getAttribute("userid");//用户id
        String cartid = (String) redisUtil.get(userid + "cartid");  //购物车id
        boolean isexist = redisUtil.hHasKey(cartid, productid);
        if (isexist) {
            Carts hget = (Carts) redisUtil.hget(cartid, productid);
            hget.setCount(hget.getCount() + 1);
            hget.setSubtotal(hget.getPrice().multiply(BigDecimal.valueOf(hget.getCount())));
            redisUtil.hset(cartid, productid, hget);
        } else {
            //发起ajax请求查询这条商品数据并将它放入redis
            String s = HttpRequestUtil.doGet("http://localhost:8084/shop/findshopbyid/" + productid, "sss");
            JSONObject jsonObject = JSONObject.parseObject(s);
            Carts carts = new Carts();
            carts.setId(jsonObject.getLong("id"));
            carts.setShopname(jsonObject.getString("name"));
            carts.setPrice(jsonObject.getBigDecimal("price"));
            carts.setShopimg(jsonObject.getString("main_img"));
            carts.setSubtotal(carts.getPrice());
            carts.setIschecked(true);
            carts.setCount(1);
            redisUtil.hset(cartid, productid, carts);
        }

    }

    @Override
    public Map<String, Object> catcarts() {
        Integer userid = (Integer) request.getAttribute("userid");
        String carts = (String) redisUtil.get(userid + "cartid"); //购物车id
        Map<Object, Object> hmget = redisUtil.hmget(carts);
        Collection<Object> values = hmget.values();
        List<Carts> cartsList = new ArrayList(values);
        Integer checkedcount = 0;                           //总个数
        BigDecimal total = new BigDecimal(0.00); //总价
        for (Carts carts1 : cartsList) {
            if (carts1.getIschecked()) {
                checkedcount += carts1.getCount();
                BigDecimal subtotal = carts1.getSubtotal();
                total = total.add(subtotal);
            }
        }
        Map<String, Object> resultmap = new HashMap<>();
        resultmap.put("cartdata", cartsList);
        resultmap.put("checkedcount", checkedcount);
        resultmap.put("total", total);
        return resultmap;
    }

    @Override
    public void incrcart(String shopid) {
        Integer userid = (Integer) request.getAttribute("userid");
        String carts = (String) redisUtil.get(userid + "cartid"); //购物车id
        boolean isexist = redisUtil.hHasKey(carts, shopid);
        if (isexist) {
            Carts hget = (Carts) redisUtil.hget(carts, shopid);
            hget.setCount(hget.getCount() + 1);
            hget.setSubtotal(hget.getPrice().multiply(BigDecimal.valueOf(hget.getCount())));
            redisUtil.hset(carts, shopid, hget);
        } else {
            System.out.println("不可能到这，我说的");
        }
    }

    @Override
    public void reduce(String shopid) {
        Integer userid = (Integer) request.getAttribute("userid");
        String carts = (String) redisUtil.get(userid + "cartid"); //购物车id
        boolean isexist = redisUtil.hHasKey(carts, shopid);
        if (isexist) {
            Carts hget = (Carts) redisUtil.hget(carts, shopid);
            hget.setCount(hget.getCount() - 1);
            hget.setSubtotal(hget.getPrice().multiply(BigDecimal.valueOf(hget.getCount())));
            redisUtil.hset(carts, shopid, hget);
        } else {
            System.out.println("不可能到这，我说的");
        }
    }

    @Override
    public void remove(String shopid) {
        Integer userid = (Integer) request.getAttribute("userid");
        String carts = (String) redisUtil.get(userid + "cartid"); //购物车id
        boolean isexist = redisUtil.hHasKey(carts, shopid);
        if (isexist) {
            redisUtil.hdel(carts, shopid);
        } else {
            System.out.println("不可能到这，我说的");
        }
    }

    @Override  //三个功能吧;
    public void uncheck(String shopid) {
        Integer userid = (Integer) request.getAttribute("userid");
        String carts = (String) redisUtil.get(userid + "cartid"); //购物车id
        boolean isexist = redisUtil.hHasKey(carts, shopid);
        if (isexist) {
            Carts hget = (Carts) redisUtil.hget(carts, shopid);
            Boolean ischecked = hget.getIschecked();
            if (ischecked) {
                hget.setIschecked(false);
                redisUtil.hset(carts, shopid, hget);
            } else {
                hget.setIschecked(true);
                redisUtil.hset(carts, shopid, hget);
            }
        } else {
            System.out.println("不可能到这，我说的");
        }
    }

    @Override
    public void isallcheck(String checkclass) {
        Integer userid = (Integer) request.getAttribute("userid");
        String carts = (String) redisUtil.get(userid + "cartid"); //购物车id
        Map<Object, Object> hmget = redisUtil.hmget(carts);
        Collection<Object> values = hmget.values();
        List<Carts> cartsList = new ArrayList(values);
        if (checkclass != null && checkclass != "") {
            for (Carts carts1 : cartsList) {
                carts1.setIschecked(false);
                redisUtil.hset(carts, String.valueOf(carts1.getId()), carts1);
            }
        } else {
            for (Carts carts1 : cartsList) {
                carts1.setIschecked(true);
                redisUtil.hset(carts, String.valueOf(carts1.getId()), carts1);
            }
        }
    }

    @Override
    public void updatecount(Integer count, String shopid) {
        Integer userid = (Integer) request.getAttribute("userid");
        String carts = (String) redisUtil.get(userid + "cartid"); //购物车id
        boolean isexist = redisUtil.hHasKey(carts, shopid);
        if (isexist) {
            Carts hget = (Carts) redisUtil.hget(carts, shopid);
            hget.setCount(count);
            hget.setSubtotal(hget.getPrice().multiply(BigDecimal.valueOf(hget.getCount())));
            redisUtil.hset(carts, shopid, hget);
        } else {
            System.out.println("不可能到这，我说的");
        }
    }


}
