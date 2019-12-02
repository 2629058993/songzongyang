package com.itzzy.commons;

import java.io.Serializable;

public class ServerResponse implements Serializable {

    private Integer code;

    private String msg;

    private Object data;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private ServerResponse(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse() {

    }

    public static ServerResponse success() {
        return new ServerResponse(200, "ok", null);
    }

    public static ServerResponse success(Object data) {
        return new ServerResponse(200, "ok", data);
    }

    public static ServerResponse error() {
        return new ServerResponse(-1, "操作失败", null);
    }

    public static ServerResponse errornologin(Integer code) {
        return new ServerResponse(code, "用户已失效请重新登录！", null);
    }

    public static ServerResponse userresponse(ResopnseEnum resopnseEnum) {
        return new ServerResponse(resopnseEnum.getCode(), resopnseEnum.getMsg(), null);
    }
}
