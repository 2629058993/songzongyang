package com.itzzy.commons;

public enum ResopnseEnum {

    USER_USER_ERROR(1002, "验证码已发送并帮您注册了账号哦！！"),
    USER_USER_SUCCESS(1004, "账号或者验证码错误!"),
    USER_USER_SUCCESSS(1023, "验证码为空或者错误!"),
    QUERY_PAYSTATUS_ERROR(1003, "查询订单失败了"),
    EWMA_IS_LOSE(1005, "二维码已失效"),
    USER_PASSWORDREALNAME_ERROR(1006, "账号密码或者真实姓名为空"),
    USER_ADD_ERROR(1007, "注册失败"),
    USER_NAMEADNPASSWORD_ERROR(1000, "账号或密码不可为空"),
    USER_LOCK_ERROR(1001, "验证码发送失败请稍后再发"),
    USER_ERRORLOCK_ERROR(1008, "已连续错误三次，账号已锁定"),
    USER_ISNO_LOGIN(1009, "用户或已失效请先登录哦!"),
    USER_OLDPASSWORD_ERROR(1010, "原密码不对应"),
    ORDER_ISNULL_ERROR(1011, "订单不存在"),
    ALL_STOCK_NULL(1012, "抱歉，订单内所有商品均无库存了"),
    ORDER_SUBMIT_ERROR(1013, "抱歉订单下单失败了!");

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ResopnseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    ResopnseEnum() {

    }
}
