package com.atwzw.aclservice.entity.vo;

import lombok.Data;

/**
 * 角色和鉴权资源的VO
 * 如：角色和用户VO
 *     角色和API
 *     角色和前端菜单
 * 他们都可以使用该VO来转换并展现给前端
 */
@Data
public class SysRoleAndPermissionVo {

    private Long  id;

    private String  name;

    private Long  roleId;

    private Long  pid;

}
