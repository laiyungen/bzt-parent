package com.atwzw.aclservice.controller;


import com.atwzw.aclservice.entity.SysPermission;
import com.atwzw.aclservice.service.SysPermissionService;
import com.atwzw.commonutils.RestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "权限菜单管理")
@RestController
@RequestMapping("/admin/acl/permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public RestResult indexAllPermission() {
        List<SysPermission> list = sysPermissionService.queryAllMenu();
        return RestResult.success().data("children", list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public RestResult remove(@ApiParam(name = "id", value = "删除菜单 ID", required = true) @PathVariable Long id) {
        sysPermissionService.removeChildById(id);
        return RestResult.success();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public RestResult doAssign(Long roleId, Long[] permissionId) {
        sysPermissionService.saveRolePermissionRealtionShipGuli(roleId, permissionId);
        return RestResult.success();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public RestResult toAssign(@ApiParam(name = "roleId", value = "角色 ID", required = true) @PathVariable Long roleId) {
        List<SysPermission> list = sysPermissionService.selectAllMenu(roleId);
        return RestResult.success().data("children", list);
    }


    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public RestResult save(@ApiParam(name = "permission", value = "新增菜单对象", required = true) @RequestBody SysPermission sysPermission) {
        return sysPermissionService.save(sysPermission) ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public RestResult updateById(@ApiParam(name = "permission", value = "修改菜单对象", required = true) @RequestBody SysPermission sysPermission) {
        return sysPermissionService.updateById(sysPermission) ? RestResult.success() : RestResult.error();
    }

}

