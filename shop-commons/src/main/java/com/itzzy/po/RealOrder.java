package com.itzzy.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class RealOrder implements Serializable {
    private String id;

    private BigDecimal pay;

    private int orderstatus;

    private Date createtime;

    private int paytype;

    private int userid;

    private int totalcount;

    private int addressid;

    private Date paytime;
}
