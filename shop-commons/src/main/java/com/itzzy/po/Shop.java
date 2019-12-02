package com.itzzy.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Shop implements Serializable {
    private long id;

    private long typeid;

    private String name;

    private String subtitle;

    private String main_img;

    private String sub_imgs;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private Date create_time;

    private Date update_time;
}
