package com.atwzw.dmsservice.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DmsDeviceDetailsDto implements Serializable {

    @ApiModelProperty(value = "设备IMEI号")
    private String deviceImei;

    @ApiModelProperty(value = "设备名")
    private String deviceName;

    @ApiModelProperty(value = "设备型号ID")
    private Long modelId;

    @ApiModelProperty("用途类别:0-轿车")
    private Integer purposeType;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "出厂时间")
    private Date exFactoryDate;

    @ApiModelProperty(value = "销售时间")
    private Date saleDate;

    @ApiModelProperty(value = "启用时间")
    private Date enableDate;

    @ApiModelProperty(value = "平台到期")
    private Date platformExpire;

}
