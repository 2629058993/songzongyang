package com.itzzy.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrderInfo implements Serializable {
    private Long id;

    private String addressname;

    private Date addtime;

    private String recipientsname;

    private String recipientsphone;

    private String email;
}
