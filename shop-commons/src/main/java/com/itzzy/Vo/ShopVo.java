package com.itzzy.Vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ShopVo implements Serializable {
    private long id;

    private String typeid;

    private String name;

    private String subtitle;

    private String main_img;

    private String sub_imgs;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    private String status;

    private String create_time;

    private String update_time;
}
