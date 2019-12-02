package com.itzzy.po;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private Long id;

    private String realname;

    private String name;

    private String password;

    private int sex;

    private int age;

    private String phone;

    private String emil;

    private Integer pay;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date ruzhitime;

    private String roleids;

    private Date logindate;

    private Integer islock;

    private Integer count;

    private Date locktime;

    private String salt;

    private Date errordate;

    private String verify;

    private String cartid;
}
