package com.realorder.Scheduler;

import com.itzzy.po.Carts;
import com.itzzy.po.ROrderdetails;
import com.itzzy.po.RealOrder;
import com.itzzy.util.RedisUtil;
import com.realorder.mapper.ROrderMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class OrdertimeoutScheduler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Resource
    private RedisUtil redisUtil;

    public static void main(String[] args) throws ParseException {
        String date1 = "2010-01-01 21:52:01";
        String date2 = "2010-01-02 21:52:01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        Date d1 = sdf.parse(date1);
        Date d2 = sdf.parse(date2);
        long cha = d2.getTime() - d1.getTime();
        double result = cha * 1.0 / (1000 * 60 * 60);
        if (result < 24) {
            System.out.println("小于12小时");
        } else {
            System.out.println("超过12小时");
        }

    }

    @Resource
    private ROrderMapper rordermapper;

    //每隔5秒执行一次
    @Scheduled(fixedRate = 5000)
    public void testTasks() {
        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
        List<RealOrder> realOrderList = rordermapper.searchorder();//查询所有待付款的订单
        if (realOrderList.size() > 0) {  //大于0说明有待付款的订单!!
            for (RealOrder realOrder : realOrderList) {
                long time = realOrder.getCreatetime().getTime();
                Date date = new Date();
                long cha = date.getTime() - time;
                double result = cha * 1.0 / (1000 * 60 * 60);
                if (result < 12) {
                    System.out.println("订单小于12小时");
                } else {
                    System.out.println("订单超过12小时");
                    String cartid = rordermapper.querycartidbyuseid(realOrder.getUserid());
                    List<ROrderdetails> rOrderdetailsList = rordermapper.queryorderdetaibyorderid(realOrder.getId());
                    for (ROrderdetails rOrderdetails : rOrderdetailsList) {
                        Carts carts = new Carts();
                        carts.setId(Long.valueOf(rOrderdetails.getShopid()));
                        carts.setCount(rOrderdetails.getShoptotalcount());
                        carts.setSubtotal(rOrderdetails.getShoptotalprice());
                        carts.setShopimg(rOrderdetails.getShopimg());
                        carts.setShopname(rOrderdetails.getShopname());
                        carts.setPrice(rOrderdetails.getShoptotalprice().divide(BigDecimal.valueOf(rOrderdetails.getShoptotalcount())));
                        carts.setIschecked(true);
                        carts.setIsstock(true);
                        //重新将订单详细数据放入购物车
                        redisUtil.hset(cartid, String.valueOf(rOrderdetails.getShopid()), carts);
                        //退还库存
                        rordermapper.addstockbyshopid(rOrderdetails.getShopid(), rOrderdetails.getShoptotalcount());
                    }
                    //删除订单
                    rordermapper.deletebyorderid(realOrder.getId());
                    //删除订单详细数据
                    rordermapper.deleteorderdetail(realOrder.getId());
                    //删除订单日志
                    rordermapper.deleteorderlog(realOrder.getId());
                }
            }
        }

    }

}