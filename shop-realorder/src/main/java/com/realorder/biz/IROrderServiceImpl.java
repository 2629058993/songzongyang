package com.realorder.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.github.wxpay.sdk.WXPay;
import com.itzzy.commons.Constant;
import com.itzzy.commons.ResopnseEnum;
import com.itzzy.commons.ServerResponse;
import com.itzzy.config.MyWxConfig;
import com.itzzy.po.Carts;
import com.itzzy.po.ROrderLog;
import com.itzzy.po.ROrderdetails;
import com.itzzy.po.RealOrder;
import com.itzzy.util.Fastaccessto;
import com.itzzy.util.HttpRequestUtil;
import com.itzzy.util.RedisUtil;
import com.itzzy.util.createEWMautils;
import com.realorder.mapper.ROrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;


@Service
public class IROrderServiceImpl implements IROrderService {
    @Resource
    private ROrderMapper rOrderMapper;

    @Resource
    private HttpServletRequest request;

    @Resource
    private RedisUtil redisUtil;


    //生成订单
    @Override
    public ServerResponse addrorder(Long recipientsid) {
        //这里的话开始处理完订单中的详细数据之后，再进行生成订单，这样有个好处就是
        //如果没有库存直接返回而不用维护订单了
        int userid = (int) request.getAttribute("userid");
        String cartis = (String) redisUtil.get(Fastaccessto.getcartid(userid));
        Map<Object, Object> hmget = redisUtil.hmget(cartis);
        Collection<Object> values = hmget.values();
        List<Carts> cartsList = new ArrayList(values);  //获取所有购物车数据
        List<Carts> Nostocklist = new ArrayList<>();   //没有库存的list,用来提示用户哪些没有了库存
        List<ROrderdetails> havestocklist = new ArrayList<>();  //有库存的集合
        int havestockcount = 0;    //有库存的个数
        long orderid = IdWorker.getId();  //订单id
        BigDecimal pay = new BigDecimal(0.00);  //付款金额;
        int Totalcount = 0;   //订单中数据总个数;
        for (Carts carts : cartsList) {
            if (carts.getIschecked()) {
                Long id = carts.getId();  //看是否有库存
                String checkshopdata = HttpRequestUtil.doGet("http://localhost:8084/shop/findshopbyid/" + id, null);
                JSONObject oshopdata = JSONObject.parseObject(checkshopdata);
                Integer stock = oshopdata.getInteger("stock");
                if (carts.getCount() > stock) {
                    Nostocklist.add(carts);
                } else {
                    //进来直接先扣除库存，防止多线程并发问题
                    int affectcount = rOrderMapper.deductstock(id, carts.getCount());
                    if (affectcount < 0) {
                        //如果不大于0就是预防了超卖,将这条购物数据归为库存不足的list中
                        Nostocklist.add(carts);
                    } else {
                        havestockcount++;
                        //真正有库存的存入订单详细数据
                        ROrderdetails rOrderdetails = new ROrderdetails();
                        rOrderdetails.setShopname(carts.getShopname());
                        rOrderdetails.setShopid(Math.toIntExact(carts.getId()));
                        rOrderdetails.setUserid(userid);
                        rOrderdetails.setShoptotalcount(carts.getCount());
                        rOrderdetails.setShoptotalprice(carts.getSubtotal());
                        rOrderdetails.setShopimg(carts.getShopimg());
                        rOrderdetails.setOrderid(String.valueOf(orderid));
                        havestocklist.add(rOrderdetails);
                        rOrderMapper.addorderdetail(rOrderdetails);//增加订单详细数据到库里;
                    }
                }
            }
        }
        if (havestockcount <= 0) {
            return ServerResponse.userresponse(ResopnseEnum.ALL_STOCK_NULL);
        }
        for (ROrderdetails rOrderdetails : havestocklist) {
            pay = pay.add(rOrderdetails.getShoptotalprice());
            Totalcount = Totalcount + rOrderdetails.getShoptotalcount();
        }
        //创建订单
        RealOrder realorder = new RealOrder();
        realorder.setId(String.valueOf(orderid));
        realorder.setAddressid(Math.toIntExact(recipientsid));
        realorder.setOrderstatus(100);  //100待付款
        realorder.setCreatetime(new Date());
        realorder.setPaytype(1);
        realorder.setUserid(userid);
        realorder.setPay(pay);
        realorder.setTotalcount(Totalcount);
        rOrderMapper.addorder(realorder);///创建订单完成
        //维护订单日志表
        ROrderLog rOrderLog = new ROrderLog();
        rOrderLog.setOutTradeNo(String.valueOf(IdWorker.getId()));
        rOrderLog.setOrderid(orderid);
        rOrderLog.setCreatetime(new Date());
        rOrderLog.setUserid(userid);
        rOrderLog.setPaymoney(pay);
        rOrderLog.setPaytype(1);
        rOrderLog.setPaystatus(100);
        rOrderMapper.addorderlog(rOrderLog); //创建订单日志.
        redisUtil.hset(userid + "orderlog", rOrderLog.getOutTradeNo(), rOrderLog, 43200L);//设置待付款订单有限时间为24小时                   //将待付款订单放入redis
        //删除redis中的数据防止被刷单。如果不删除这数据还会展示，那用户就可以一直提交订单来让库存不足。
        for (ROrderdetails rOrderdetails : havestocklist) {
            redisUtil.hdel(cartis, String.valueOf(rOrderdetails.getShopid()));
        }
        //将没有库存数据和订单号id进行返回
        Map<String, Object> resultmap = new HashMap<>();
        resultmap.put("nostocklist", Nostocklist);
        resultmap.put("orderid", String.valueOf(orderid));
        resultmap.put("outTradeNo", rOrderLog.getOutTradeNo());
        return ServerResponse.success(resultmap);
    }


    //生成二维码
    @Override
    public ServerResponse initEWMA(String outTradeNo) {
        int userid = (int) request.getAttribute("userid");
        //获取待付款订单
        ROrderLog hget = (ROrderLog) redisUtil.hget(userid + "orderlog", outTradeNo);
        if (hget == null) {
            return ServerResponse.userresponse(ResopnseEnum.ORDER_ISNULL_ERROR);
        }
        //微信下单.返回下单各种状态
        Map<String, String> orderdata = createEWMautils.getOrderdata(outTradeNo, hget.getPaymoney());
        //判断状态，为true就是下单成功
        boolean verifyissuccess = createEWMautils.verifyissuccess(orderdata);
        if (!verifyissuccess) {
            return ServerResponse.userresponse(ResopnseEnum.ORDER_SUBMIT_ERROR);
        }
        Map<String, Object> resultmap = new HashMap<>();
        String code_url = orderdata.get("code_url"); //如果下单成功就获取图片路径；
        resultmap.put("EWMAurl", code_url);
        resultmap.put("paymoney", hget.getPaymoney());
        return ServerResponse.success(resultmap);
    }


    //扫描订单是否支付。
    @Override
    public ServerResponse detectionisqr(long orderid, String outTradeNo) {
        int userid = (int) request.getAttribute("userid");
        //获取待付款订单
        ROrderLog hget = (ROrderLog) redisUtil.hget(userid + "orderlog", outTradeNo);
        if (hget == null) {
            return ServerResponse.userresponse(ResopnseEnum.ORDER_ISNULL_ERROR);
        }
        MyWxConfig myWxConfig = new MyWxConfig();
        int count = 0;
        while (true) {
            try {
                WXPay wxPay = new WXPay(myWxConfig);
                Map<String, String> stringmap = new HashMap<String, String>();
                stringmap.put("out_trade_no", outTradeNo);
                Map<String, String> stringStringMap = wxPay.orderQuery(stringmap);
                boolean verifyissuccess = createEWMautils.verifyissuccess(stringStringMap);
                if (!verifyissuccess) {
                    return ServerResponse.userresponse(ResopnseEnum.QUERY_PAYSTATUS_ERROR);
                }
                String trade_state = stringStringMap.get("trade_state");
                if (!"SUCCESS".equalsIgnoreCase(trade_state)) {
                    count++;
                    Thread.sleep(3000);
                    if (count >= 50) {
                        //只要有一次验证码失效就启动定时器，去扫描数据库中的订单创建时间

                        return ServerResponse.userresponse(ResopnseEnum.EWMA_IS_LOSE);
                    }
                } else {
                    //到这里就是支付成功了
                    RealOrder realOrder = new RealOrder();
                    realOrder.setId(String.valueOf(orderid));
                    realOrder.setPaytime(new Date());
                    realOrder.setOrderstatus(Constant.PAY_STATUS);
                    rOrderMapper.updateorderstatus(realOrder);//修改订单状态

                    ROrderLog rOrderLog = new ROrderLog();
                    String transaction_id = stringStringMap.get("transaction_id");
                    rOrderLog.setPaystatus(Constant.PAY_STATUS);
                    rOrderLog.setPayTime(new Date());
                    rOrderLog.setTransactionId(transaction_id);
                    rOrderLog.setOutTradeNo(outTradeNo);
                    rOrderMapper.updateorderlogstatus(rOrderLog); //修改订单日志状态

                    //删除redis中待付款数据
                    redisUtil.hdel(userid + "orderlog", outTradeNo);
                    return ServerResponse.success();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
