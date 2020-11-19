package com.atwzw.amsservice.enums;

/**
 * @API
 * @Author lyg
 * @Description 报警信息状态类：读取状态:0-未读,1-已读,2-未处理,3-已处理
 * @Date 2020/10/23 22:53
 * @Param
 * @return
 **/
public enum AlertStatusEnum {

    UNREAD(0,"未读"),
    READ(1,"已读"),

    UNTREATED(2,"未处理"),
    PROCESSED(3,"已处理");

    private Integer status;

    private String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    AlertStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
