package com.atwzw.dmsservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 车辆信息表
 * </p>
 *
 * @author lyg
 * @since 2020-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DmsVehicleMsg对象", description="车辆信息表")
public class DmsVehicleMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "车辆型号 ID")
    private Long vehicleModelId;

    @ApiModelProperty(value = "车主名")
    private String vehicleOwnerName;

    @ApiModelProperty(value = "车主电话")
    private String vehicleOwnerTel;

    @ApiModelProperty(value = "紧急联系人")
    private String emergencyContact;

    @ApiModelProperty(value = "紧急联系人电话")
    private String emergencyContactTel;

    @ApiModelProperty(value = "身份证号")
    private String idNumber;

    @ApiModelProperty(value = "车牌号")
    private String plateNumber;

    @ApiModelProperty(value = "车架号/VIN码")
    private String carframeNumber;

    @ApiModelProperty(value = "车辆登记证书")
    private String vehicleRegisterCertificate;

    @ApiModelProperty(value = "车辆购买时间")
    private Date purchaseDate;

    @ApiModelProperty(value = "用途类别:0-轿车")
    private Boolean purposeType;

    @ApiModelProperty(value = "逻辑删除:0-未删除,1-已删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
