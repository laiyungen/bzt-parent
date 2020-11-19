package com.atwzw.security.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体类
 */
@Data
@ApiModel(description = "用户实体类")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户名称")
	private String username;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "登录名")
	private String loginNo;

	@ApiModelProperty(value = "用户头像")
	private String salt;

	@ApiModelProperty(value = "记住我")
	private Boolean rememberMe;

}



