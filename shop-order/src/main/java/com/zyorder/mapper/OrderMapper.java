package com.zyorder.mapper;

import com.itzzy.po.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderMapper {
    List<OrderInfo> queryaddress(Integer userid);

    void checkrecipient(@Param("id") Integer recipientid, @Param("date") Date date);

    OrderInfo queryorderbyid(Integer recipientid);

    void addrecipients(OrderInfo orderInfo);

    void addorderanduser(@Param("id") Long id, @Param("userid") Integer userid);

    void updaterecipients(OrderInfo orderInfo);

    void deleterecipients(Integer recipientsid);

    void delrecipientsanduser(Integer recipientsid);
}
