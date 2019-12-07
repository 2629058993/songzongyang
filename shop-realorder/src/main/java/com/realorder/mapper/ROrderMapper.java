package com.realorder.mapper;

import com.itzzy.po.ROrderLog;
import com.itzzy.po.ROrderdetails;
import com.itzzy.po.RealOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ROrderMapper {
    int deductstock(@Param("id") Long id, @Param("count") int count);

    void addorderdetail(ROrderdetails rOrderdetails);

    void addorder(RealOrder realorder);

    void addorderlog(ROrderLog rOrderLog);

    void updateorderstatus(RealOrder realOrder);

    void updateorderlogstatus(ROrderLog rOrderLog);

    List<RealOrder> searchorder();

    String querycartidbyuseid(int userid);

    List<ROrderdetails> queryorderdetaibyorderid(String id);

    void addstockbyshopid(@Param("shopid") int shopid, @Param("stock") int shoptotalcount);

    void deletebyorderid(String id);

    void deleteorderdetail(String id);

    void deleteorderlog(String id);
}
