package com.zyorder.biz;

import com.itzzy.po.OrderInfo;
import com.zyorder.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class IOrderServiceImpl implements IOrderService {
    @Resource
    private OrderMapper ordermapper;

    @Resource
    private HttpServletRequest request;

    @Override
    public List<OrderInfo> queryaddress() {
        Integer userid = (Integer) request.getAttribute("userid");
        List<OrderInfo> queryaddress = ordermapper.queryaddress(userid);//找出该用户对应的地址
        for (OrderInfo orderInfo : queryaddress) {
            orderInfo.setRecipientsphone(orderInfo.getRecipientsphone()
                    .substring(0, 3) + "****" + orderInfo.getRecipientsphone()
                    .substring(7, orderInfo.getRecipientsphone().length()));
        }
        return queryaddress;
    }

    @Override
    public OrderInfo checkrecipient(Integer recipientid) {
        Date date = new Date();
        ordermapper.checkrecipient(recipientid, date);
        OrderInfo orderInfo = ordermapper.queryorderbyid(recipientid);
        orderInfo.setRecipientsphone(orderInfo.getRecipientsphone()
                .substring(0, 3) + "****" + orderInfo.getRecipientsphone()
                .substring(7, orderInfo.getRecipientsphone().length()));
        return orderInfo;
    }

    @Override
    public void addrecipients(OrderInfo orderInfo) {
        orderInfo.setAddtime(new Date());
        ordermapper.addrecipients(orderInfo);
        Integer userid = (Integer) request.getAttribute("userid");
        ordermapper.addorderanduser(orderInfo.getId(), userid);  //维护用户收件人中间表;
    }

    @Override
    public OrderInfo echorecipients(Integer recipientsid) {
        OrderInfo orderInfo = ordermapper.queryorderbyid(recipientsid);
        return orderInfo;
    }

    @Override
    public void updaterecipients(OrderInfo orderInfo) {
        orderInfo.setAddtime(new Date());
        ordermapper.updaterecipients(orderInfo);
    }

    @Override
    public void deleterecipients(Integer recipientsid) {
        ordermapper.deleterecipients(recipientsid);
        ordermapper.delrecipientsanduser(recipientsid);
    }
}
