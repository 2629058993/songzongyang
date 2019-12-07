package com.itzzy.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ROrderdetails implements Serializable {
    private String orderid;

    private int userid;

    private String shopname;

    private BigDecimal shoptotalprice;

    private int shoptotalcount;

    private int shopid;

    private String shopimg;
}
