package com.atwzw.dmsservice.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DmsDeviceListDto implements Serializable {

    @ApiModelProperty(value = "设备IMEI号")
    private String deviceImei;

    @ApiModelProperty(value = "设备名")
    private String deviceName;

    @ApiModelProperty(value = "设备型号名称")
    private String modelName;

    @ApiModelProperty(value = "SIM 卡的 ICCID 号")
    private String iccid;

    @ApiModelProperty(value = "SIM 卡的 MSISDN 号")
    private String msisdn;

    @ApiModelProperty("激活时间")
    private Date activationDate;

    @ApiModelProperty(value = "卡到期时间")
    private Date cardExpiry;

    @ApiModelProperty(value = "平台到期时间")
    private Date platformExpire;

    @ApiModelProperty("设备状态:1-行驶,2-静止,3-离线,4-未启用,5-已过期")
    private Integer deviceSts;

}
