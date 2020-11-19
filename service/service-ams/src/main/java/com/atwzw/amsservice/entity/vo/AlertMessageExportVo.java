package com.atwzw.amsservice.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(25)
@ApiModel(value = "AlertMessageExportVo",description = "报警信息导出实体类")
public class AlertMessageExportVo extends BaseRowModel {

    @ExcelProperty(value = "设备IMEI号",index = 0)
    @ApiModelProperty(value = "设备IMEI号")
    private String deviceImei;

    @ExcelProperty(value = "设备名称",index = 1)
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ExcelProperty(value = "设备型号",index = 2)
    @ApiModelProperty(value = "设备型号")
    private String modelName;

    @ExcelProperty(value = "报警类型名称",index = 3)
    @ApiModelProperty(value = "报警类型名称")
    private String typeName;

    @ExcelProperty(value = "报警消息时间",index = 4)
    @ApiModelProperty(value = "报警消息时间")
    private Date msgTime;

    @ExcelProperty(value = "设备 GPS 定位的时间",index = 5)
    @ApiModelProperty(value = "设备 GPS 定位的时间")
    private Date locationTime;

    @ExcelProperty(value = "速度(km/h)",index = 6)
    @ApiModelProperty(value = "速度(km/h)")
    private BigDecimal speed;

    @ExcelProperty(value = "方向",index = 7)
    @ApiModelProperty(value = "方向")
    private Integer direction;

    @ExcelProperty(value = "原始经度值",index = 8)
    @ApiModelProperty(value = "原始经度值")
    private BigDecimal longitude;

    @ExcelProperty(value = "原始纬度值",index = 9)
    @ApiModelProperty(value = "原始纬度值")
    private BigDecimal latitude;

}
