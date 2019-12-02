package com.itzzy.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Type implements Serializable {
    private long id;

    private String name;

    private long pid;
}
