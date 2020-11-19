package com.atwzw.amsservice.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 报警信息表
 * </p>
 *
 * @author lyg
 * @since 2020-09-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AmsAlertMessage对象", description="报警信息表")
public class AmsAlertMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "报警消息时间")
    private Date msgTime;

    @ApiModelProperty(value = "报警类型ID")
    private Integer typeId;

    @ApiModelProperty(value = "报警类型名称")
    private String typeName;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "设备IMEI号")
    private String deviceImei;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备型号 ID")
    private Long modelId;

    @ApiModelProperty(value = "设备型号")
    private String modelName;

    @ApiModelProperty(value = "设备GPS定位的时间")
    private Date locationTime;

    @ApiModelProperty(value = "速度(km/h)")
    private BigDecimal speed;

    @ApiModelProperty(value = "方向")
    private Integer direction;

    @ApiModelProperty(value = "定位状态:0-离线,1-在线")
    private String locateSts;

    @ApiModelProperty(value = "acc状态:0-关闭,1-开启")
    private String accSts;

    @ApiModelProperty(value = "E:经度")
    private String longitudeEw;

    @ApiModelProperty(value = "原始经度值")
    private BigDecimal longitude;

    @ApiModelProperty(value = "N:纬度")
    private String latitudeNs;

    @ApiModelProperty(value = "原始纬度值")
    private BigDecimal latitude;

    @ApiModelProperty(value = "百度地图经度")
    private BigDecimal baiduLon;

    @ApiModelProperty(value = "百度地图纬度")
    private BigDecimal baiduLat;

    @ApiModelProperty(value = "高德地图经度")
    private BigDecimal gaodeLon;

    @ApiModelProperty(value = "高德地图纬度")
    private BigDecimal gaodeLat;

    @ApiModelProperty(value = "腾讯地图经度")
    private BigDecimal qqLon;

    @ApiModelProperty(value = "腾讯地图纬度")
    private BigDecimal qqLat;

    @ApiModelProperty(value = "谷歌地图经度")
    private BigDecimal googleLon;

    @ApiModelProperty(value = "谷歌地图纬度")
    private BigDecimal googleLat;

    @ApiModelProperty(value = "状态:0-未读;1-已读;2-未处理;3-已处理;")
    private Integer status;

    @ApiModelProperty(value = "逻辑删除:0-未删除,1-已删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
