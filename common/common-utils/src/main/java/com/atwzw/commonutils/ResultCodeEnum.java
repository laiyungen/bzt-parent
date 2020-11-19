package com.atwzw.commonutils;

/**
 * @API
 * @Author lyg
 * @Description 请求状态类：该类设定后传至前台从而响应给客户端
 * @Date 2020/09/18 22:53
 * @Param
 * @return
 **/
public enum ResultCodeEnum {

    CD200(200,"成功"),
    CD201(201,"失败"),

    CD205(205,"网关失败"),
    CD284(284,"鉴权失败"),
    CD285(285,"远程调用失败"),

    CD301(301,"您没有访问该API的权限！"),
    CD302(302,"不是UserDetails类型！"),

    CD502(502,"不存在该用户"),
    CD503(503,"无权转移该用户"),
    CD504(504,"导出失败！"),

    CD505(505,"不能删除拥有下级的用户！"),
    CD506(506,"不能删除拥有设备的用户！");

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ResultCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
