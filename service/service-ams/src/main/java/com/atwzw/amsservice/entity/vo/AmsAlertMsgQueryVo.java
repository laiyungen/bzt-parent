package com.atwzw.amsservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AmsAlertMsgQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "设备IMEI号")
    private String deviceImei;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备型号 ID")
    private Long modelId;

    @ApiModelProperty(value = "报警类型ID")
    private Integer typeId;

    @ApiModelProperty(value = "报警开始时间")
    private String startTimeStr; // 注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间")
    private String endTimeStr;

    @ApiModelProperty(value = "是否包含下级")
    private Boolean isContainSub = false;

}
