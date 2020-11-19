package com.atwzw.servicebase.bean;

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
 * 用户表
 * </p>
 *
 * @author lyg
 * @since 2020-09-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "BztUser对象", description = "用户表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户类型：1-普通用户;2-经销商")
    private Integer userType;

    @ApiModelProperty(value = "父级ID")
    private Long pid;

    @ApiModelProperty(value = "父级用户别名")
    private String parentName;

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "别名")
    private String aliasName;

    @ApiModelProperty(value = "用户头像")
    private String headPortraitUrl;

    @ApiModelProperty(value = "系统肤色")
    private String skinColour;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "联系人")
    private String linkman;

    @ApiModelProperty(value = "联系电话")
    private String tel;

    @ApiModelProperty(value = "地址")
    private String addr;

    @ApiModelProperty(value = "进货数")
    private Long purchaseCount;

    @ApiModelProperty(value = "库存数")
    private Long stockCount;

    @ApiModelProperty(value = "钱包余额")
    private BigDecimal walletBalance;

    @ApiModelProperty(value = "可退余额")
    private BigDecimal returnableBalance;

    @ApiModelProperty(value = "不可退余额")
    private BigDecimal nonRefundableBalance;

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
