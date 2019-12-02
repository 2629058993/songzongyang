package com.itzzy.Vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {
    private Long id;

    private String realname;

    private String name;

    private String password;

    private int sex;

    private int age;

    private String phone;

    private String emil;

    private Integer pay;

    private String ruzhitime;
}
