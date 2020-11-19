package com.atwzw.smsservice.entity.vo;

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
@ApiModel(value = "SimCardExportVo",description = "SIM 卡信息导出实体类")
public class SimCardExportVo extends BaseRowModel {

    @ExcelProperty(value = "设备IMEI号",index = 0)
    @ApiModelProperty(name = "deviceImei", value = "设备IMEI号")
    private String deviceImei;

    @ExcelProperty(value = "SIM卡的ICCID号",index = 1)
    @ApiModelProperty(name = "iccid", value = "SIM卡的ICCID号")
    private String iccid;

    @ExcelProperty(value = "SIM卡的MSISDN号",index = 2)
    @ApiModelProperty(name = "msisdn", value = "SIM卡的MSISDN号")
    private String msisdn;

    @ExcelProperty(value = "套餐类型, 如 5M/10M/30M",index = 3)
    @ApiModelProperty(name = "type", value = "套餐类型, 如 5M/10M/30M")
    private String type;

    @ExcelProperty(value = "供应商 ID, 如 S001SW/S002GM",index = 4)
    @ApiModelProperty(name = "supplierId", value = "供应商 ID, 如 S001SW/S002GM")
    private String supplierId;

    @ExcelProperty(value = "供应商名称, 如 斯沃德/谷米",index = 5)
    @ApiModelProperty(name = "supplierName", value = "供应商名称, 如 斯沃德/谷米")
    private String supplierName;

    @ExcelProperty(value = "激活时间",index = 6)
    @ApiModelProperty(name = "activationDate", value = "激活时间")
    private Date activationDate;

    @ExcelProperty(value = "流量到期时间",index = 7)
    @ApiModelProperty(name = "flowExpiry", value = "流量到期时间")
    private Date flowExpiry;

    @ExcelProperty(value = "卡到期时间",index = 8)
    @ApiModelProperty(name = "cardExpiry", value = "卡到期时间")
    private Date cardExpiry;

    @ExcelProperty(value = "剩余流量",index = 9)
    @ApiModelProperty(name = "residualFlow", value = "剩余流量")
    private BigDecimal residualFlow;

}
