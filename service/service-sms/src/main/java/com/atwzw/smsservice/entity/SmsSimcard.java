package com.atwzw.smsservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * SIM 卡信息表
 * </p>
 *
 * @author lyg
 * @since 2020-09-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SmsSimcard对象", description = "SIM 卡信息表")
public class SmsSimcard implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "设备IMEI号")
    private String deviceImei;

    @ApiModelProperty(value = "SIM卡的ICCID号")
    private String iccid;

    @ApiModelProperty(value = "SIM卡的MSISDN号")
    private String msisdn;

    @ApiModelProperty(value = "套餐类型, 如 5M/10M/30M")
    private String type;

    @ApiModelProperty(value = "供应商 ID, 如 S001SW/S002GM")
    private String supplierId;

    @ApiModelProperty(value = "供应商名称, 如 斯沃德/谷米")
    private String supplierName;

    @ApiModelProperty(value = "激活时间")
    private Date activationDate;

    @ApiModelProperty(value = "流量到期时间")
    private Date flowExpiry;

    @ApiModelProperty(value = "卡到期时间")
    private Date cardExpiry;

    @ApiModelProperty(value = "剩余流量")
    private BigDecimal residualFlow;

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
