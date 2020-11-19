package com.atwzw.aclservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SysUserQueryVo {

    @ApiModelProperty(value = "当前用户 ID")
    private Long id;

    @ApiModelProperty(value = "下级用户 ID 集合")
    private List<Long> userIdList;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "用户类型:0-普通用户,1-经销商")
    private Integer roleId;

    @ApiModelProperty(value = "是否包含下级")
    private Boolean isContainSub = false;

    @ApiModelProperty(value = "别名")
    private String aliasName;

}
