package com.itzzy.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Brand implements Serializable {
    private long id;

    private String brandname;

    private String imgurl;
}
