package com.itzzy.util;

import com.github.wxpay.sdk.WXPay;
import com.itzzy.Exeception.DaoException;
import com.itzzy.commons.ResopnseEnum;
import com.itzzy.config.MyWxConfig;
import org.apache.commons.lang.time.DateUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class createEWMautils {
    //下单生成二维码
    public static Map<String, String> getOrderdata(String outTradeNo, BigDecimal payMoney) {
        //调用支付接口
        MyWxConfig myWxConfig = new MyWxConfig();
        Map<String, String> resulMap = null;
        try {
            WXPay wxPay = new WXPay(myWxConfig);
            //存放参数的map集合
            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "飞狐1904B电商支付--平台");
            //设置微信订单号  就是我们的支付单号
            data.put("out_trade_no", outTradeNo);
            //设置币种
            data.put("fee_type", "CNY");
           /* //设置支付的失效时间
            Date date = DateUtils.addMinutes(new Date(), 2);
            String s = dateutil.date2string(date, dateutil.YYYY_MM_DD_HH_MM_SS);
            data.put("time_expire",s);*/
            //设置支付的金额
            //BigDecimalUtil.mul(payLog.getPayMoney()+"", "100").intValue();
            BigDecimal multiply = payMoney.multiply(BigDecimal.valueOf(100));
            int payprice = multiply.intValue();//乘以100并且转为int类型
            data.put("total_fee", payprice + "");
            //设置接口的调用路径
            data.put("notify_url", "http://www.example.com/wxpay/notify");
            // 此处指定为扫码支付
            data.put("trade_type", "NATIVE");
            resulMap = wxPay.unifiedOrder(data);  //执行下单操作
        } catch (Exception e) {
            throw new DaoException(ResopnseEnum.ORDER_SUBMIT_ERROR);
        }
        return resulMap;
    }


    //验证接口是否调用成功
    public static boolean verifyissuccess(Map<String, String> resulMap) {
        //验证接口是否能够正常访问
        String returnCode = resulMap.get("return_code");
        String returnMsg = resulMap.get("return_msg");
        if (!"SUCCESS".equalsIgnoreCase(returnCode)) {
            return false;
        }
        //验证业务是否成功
        String resultCode = resulMap.get("result_code");
        String errorCodeDes = resulMap.get("err_code_des");
        if (!"SUCCESS".equalsIgnoreCase(resultCode)) {
            return false;
        }
        return true;
    }

}
