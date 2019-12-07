package com.itzzy.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ROrderLog implements Serializable {
    private String outTradeNo;

    private Date createtime;

    private long orderid;

    private int userid;

    private String transactionId;

    private Date payTime;

    private BigDecimal paymoney;

    private int paytype;

    private int paystatus;
}
