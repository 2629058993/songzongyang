package com.itzzy.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Carts implements Serializable {
    private Long id;

    private String shopname;

    private String shopimg;

    private BigDecimal price;

    private BigDecimal subtotal;

    private int count;

    private Boolean ischecked;
}
