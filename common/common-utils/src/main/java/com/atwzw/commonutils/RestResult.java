package com.atwzw.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @API
 * @Author lyg
 * @Description 返回前端模板数据对象
 * @Date 2020/09/18 22:53
 * @Param
 * @return
 **/
@Data
public class RestResult {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    //把构造方法私有
    private RestResult() {}

    //成功静态方法
    public static RestResult success() {
        RestResult restResult = new RestResult();
        restResult.setSuccess(true);
        restResult.setCode(ResultCodeEnum.CD200.getCode());
        restResult.setMessage(ResultCodeEnum.CD200.getMsg());
        return restResult;
    }

    //失败静态方法
    public static RestResult error() {
        RestResult restResult = new RestResult();
        restResult.setSuccess(false);
        restResult.setCode(ResultCodeEnum.CD201.getCode());
        restResult.setMessage(ResultCodeEnum.CD201.getMsg());
        return restResult;
    }

    public RestResult success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public RestResult message(String message){
        this.setMessage(message);
        return this;
    }

    public RestResult code(Integer code){
        this.setCode(code);
        return this;
    }

    public RestResult data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public RestResult data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
