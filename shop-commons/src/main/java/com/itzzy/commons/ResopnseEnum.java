package com.itzzy.commons;

public enum ResopnseEnum {

    USER_USER_ERROR(1002, "验证码已发送并帮您注册了账号哦！！"),
    USER_USER_SUCCESS(1004, "账号或者验证码错误!"),
    USER_USER_SUCCESSS(1023, "验证码为空或者错误!"),
    USER_PASSWORDD_ERROR(1003, "密码错误"),
    USER_ZHUCE_ERROR(1005, "用户已存在"),
    USER_PASSWORDREALNAME_ERROR(1006, "账号密码或者真实姓名为空"),
    USER_ADD_ERROR(1007, "注册失败"),
    USER_NAMEADNPASSWORD_ERROR(1000, "账号或密码不可为空"),
    USER_LOCK_ERROR(1001, "验证码发送失败请稍后再发"),
    USER_ERRORLOCK_ERROR(1008, "已连续错误三次，账号已锁定"),
    USER_ISNO_LOGIN(1009, "用户或已失效请先登录哦!"),
    USER_OLDPASSWORD_ERROR(1010, "原密码不对应"),
    USER_SHRUEPASSWORD_ERROR(1011, "确认密码有误"),
    USER_EMIL_ERROR(1012, "emil不可为空"),
    USER_ISPRAYUSER_ERROR(1013, "没有查到该邮箱对应的用户!");

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
