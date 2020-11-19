package com.atwzw.servicebase.bean;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author lyg
 * @since 2020-09-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DmsDevice对象", description="设备表")
public class DmsDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键 ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "设备IMEI号")
    private String deviceImei;

    @ApiModelProperty(value = "设备名")
    private String deviceName;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户别名")
    private String aliasName;

    @ApiModelProperty(value = "设备分组 ID")
    private Long groupId;

    @ApiModelProperty(value = "是否被关注:0-未关注,1-已关注")
    private Long isFollow;

    @ApiModelProperty(value = "设备型号ID")
    private Long modelId;

    @ApiModelProperty(value = "设备型号名称")
    private String modelName;

    @ApiModelProperty(value = "车辆ID")
    private Long vehicleId;

    @ApiModelProperty(value = "出厂时间")
    private Date exFactoryDate;

    @ApiModelProperty(value = "导入时间")
    private Date importTime;

    @ApiModelProperty(value = "销售时间")
    private Date saleDate;

    @ApiModelProperty(value = "启用时间")
    private Date enableDate;

    @ApiModelProperty(value = "平台到期")
    private Date platformExpire;

    @ApiModelProperty(value = "显示设备ICON")
    private String icon;

    @ApiModelProperty(value = "SIM卡的ID号")
    private Long simcardId;

    @ApiModelProperty(value = "备注")
    private String remark;

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
