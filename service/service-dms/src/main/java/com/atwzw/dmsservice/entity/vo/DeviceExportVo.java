package com.atwzw.dmsservice.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(25)
@ApiModel(value = "DeviceExportVo",description = "设备信息导出实体类")
public class DeviceExportVo extends BaseRowModel {

    @ExcelProperty(value = "设备IMEI号",index = 0)
    @ApiModelProperty(name = "deviceImei", value = "设备IMEI号")
    private String deviceImei;

    @ExcelProperty(value = "设备名",index = 1)
    @ApiModelProperty(name = "deviceName", value = "设备名")
    private String deviceName;

    @ExcelProperty(value = "用户别名",index = 2)
    @ApiModelProperty(name = "aliasName", value = "用户别名")
    private String aliasName;

    @ExcelProperty(value = "设备型号名称",index = 3)
    @ApiModelProperty(name = "modelName", value = "设备型号名称")
    private String modelName;

    @ExcelProperty(value = "出厂时间",index = 4)
    @ApiModelProperty(name = "exFactoryDate", value = "出厂时间")
    private Date exFactoryDate;

    @ExcelProperty(value = "导入时间",index = 5)
    @ApiModelProperty(name = "importTime", value = "导入时间")
    private Date importTime;

    @ExcelProperty(value = "销售时间",index = 6)
    @ApiModelProperty(name = "saleDate", value = "销售时间")
    private Date saleDate;

    @ExcelProperty(value = "启用时间",index = 7)
    @ApiModelProperty(name = "enableDate", value = "启用时间")
    private Date enableDate;

    @ExcelProperty(value = "平台到期",index = 8)
    @ApiModelProperty(name = "platformExpire", value = "平台到期")
    private Date platformExpire;

}
