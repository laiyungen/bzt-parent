package com.atwzw.dmsservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DmsDeviceQueryVo implements Serializable {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户 ID 集合")
    private List<Long> userIdList;

    @ApiModelProperty(value = "设备IMEI号")
    private String deviceImei;

    @ApiModelProperty(value = "设备名")
    private String deviceName;

    @ApiModelProperty("车牌号")
    private String plateNumber;

    @ApiModelProperty(value = "设备型号ID")
    private Long modelId;

    @ApiModelProperty("平台是否过期")
    private Boolean isPlatformExpire;

    @ApiModelProperty("卡是否过期")
    private Boolean isEardExpiry;

    @ApiModelProperty("设备状态:1-行驶,2-静止,3-离线,4-未启用,5-已过期")
    private Integer deviceSts;

    @ApiModelProperty("起始激活时间")
    private Date startActivationDate;

    @ApiModelProperty("结束激活时间")
    private Date endActivationDate;

    @ApiModelProperty(value = "是否包含下级用户设备")
    private Boolean isContainSub = false;

}

