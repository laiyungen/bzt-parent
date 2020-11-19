package com.atwzw.aclservice.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SysUserTreeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "当前用户 ID")
    private Long userId;

    @ApiModelProperty(value = "当前用户别名")
    private String aliasName;

    @ApiModelProperty(value = "是否有子集用户")
    private Boolean isSub;

    @ApiModelProperty(value = "子集用户集合")
    private List<SysUserTreeDto> children = new ArrayList<>();

}