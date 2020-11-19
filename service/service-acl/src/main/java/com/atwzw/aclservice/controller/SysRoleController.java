package com.atwzw.aclservice.controller;


import com.atwzw.aclservice.entity.SysRole;
import com.atwzw.aclservice.entity.vo.SysRoleAndPermissionVo;
import com.atwzw.aclservice.service.SysRoleService;
import com.atwzw.commonutils.RestResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "角色处理器")
@RestController
@RequestMapping("/admin/acl/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("{page}/{limit}")
    public RestResult index(
            @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
            @ApiParam(name = "role", value = "查询角色条件对象") SysRole sysRole) {
        Page<SysRole> pageParam = new Page<>(page, limit);
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(sysRole.getRoleName())) {
            wrapper.like("role_name", sysRole.getRoleName());
        }
        sysRoleService.page(pageParam, wrapper);
        return RestResult.success().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("get/{id}")
    public RestResult get(@ApiParam(name = "id", value = "获取角色 ID", required = true) @PathVariable long id) {
        SysRole sysRole = sysRoleService.getById(id);
        return RestResult.success().data("item", sysRole);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public RestResult save(@ApiParam(name = "role", value = "新增角色对象", required = true) @RequestBody SysRole sysRole) {
        return sysRoleService.save(sysRole) ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public RestResult updateById(@ApiParam(name = "role", value = "修改角色对象", required = true) @RequestBody SysRole sysRole) {
        return sysRoleService.updateById(sysRole) ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public RestResult remove(@ApiParam(name = "id", value = "删除角色 ID", required = true) @PathVariable long id) {
        return sysRoleService.removeById(id) ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("batchRemove")
    public RestResult batchRemove(@ApiParam(name = "idList", value = "删除角色 ID 集合", required = true) @RequestBody List<Long> idList) {
        return sysRoleService.removeByIds(idList) ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping("getRoleList")
    public RestResult getRoleList() {
        List<SysRole> sysRoleList = sysRoleService.selectRoleList();
        return RestResult.success().data("roleList", sysRoleList);
    }

}

