package com.atwzw.aclservice.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ContentRowHeight(10)
@HeadRowHeight(20)
@ColumnWidth(25)
@ApiModel(value = "DeviceExportVo",description = "用户信息导出实体类")
public class UserExportVo extends BaseRowModel {

    @ExcelProperty(value = "用户名称",index = 0)
    @ApiModelProperty(name = "username", value = "用户名称")
    private String username;

    @ExcelProperty(value = "别名",index = 1)
    @ApiModelProperty(name = "aliasName",value = "别名")
    private String aliasName;

    @ExcelProperty(value = "父级用户别名",index = 2)
    @ApiModelProperty(name = "parentName",value = "父级用户别名")
    private String parentName;

    @ExcelProperty(value = "联系人",index = 3)
    @ApiModelProperty(name = "linkman",value = "联系人")
    private String linkman;

    @ExcelProperty(value = "联系电话",index = 4)
    @ApiModelProperty(name = "tel",value = "联系电话")
    private String tel;

    @ExcelProperty(value = "地址",index = 5)
    @ApiModelProperty(name = "addr",value = "地址")
    private String addr;

    @ExcelProperty(value = "进货数",index = 6)
    @ApiModelProperty(name = "purchaseCount",value = "进货数")
    private Long purchaseCount;

    @ExcelProperty(value = "库存数",index = 7)
    @ApiModelProperty(name = "stockCount",value = "库存数")
    private Long stockCount;

}
