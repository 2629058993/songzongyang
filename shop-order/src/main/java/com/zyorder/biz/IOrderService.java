package com.zyorder.biz;

import com.itzzy.po.OrderInfo;

import java.util.List;

public interface IOrderService {
    List<OrderInfo> queryaddress();

    OrderInfo checkrecipient(Integer recipientid);

    void addrecipients(OrderInfo orderInfo);

    OrderInfo echorecipients(Integer recipientsid);

    void updaterecipients(OrderInfo orderInfo);

    void deleterecipients(Integer recipientsid);
}
