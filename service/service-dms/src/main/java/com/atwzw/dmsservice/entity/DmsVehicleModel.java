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
 * 车辆型号表
 * </p>
 *
 * @author lyg
 * @since 2020-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DmsVehicleModel对象", description="车辆型号表")
public class DmsVehicleModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "品牌")
    private String brand;

    @ApiModelProperty(value = "车辆型号")
    private String vehicleModel;

    @ApiModelProperty(value = "车系")
    private String vehicleSystem;

    @ApiModelProperty(value = "颜色")
    private Boolean colour;

    @ApiModelProperty(value = "逻辑删除:0-未删除,1-已删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
