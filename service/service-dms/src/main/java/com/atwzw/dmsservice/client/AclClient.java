package com.atwzw.dmsservice.client;

import com.atwzw.commonutils.RestResult;
import com.atwzw.dmsservice.client.fuse.AclClientFuse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 用户资源管理远程调用服务类
 */
@FeignClient(name = "service-acl", fallback = AclClientFuse.class)
@Component
public interface AclClient {

    @ApiOperation(value = "获取管理用户信息")
    @GetMapping("/admin/acl/user/get/{id}")
    RestResult getUserById(@ApiParam(name = "id", value = "查询用户 ID", required = true) @PathVariable("id") long id);

    @ApiOperation(value = "根据父级用户 ID 查询出所有下级用户信息集合")
    @GetMapping("/admin/acl/user/getAllChildUser/{userId}")
    RestResult getAllChildrenSysUserListByPid(@ApiParam(name = "userId", value = "父级用户 ID", required = true) @PathVariable("userId") long userId);

    @ApiOperation(value = "根据 token 获取用户信息")
    @GetMapping("/admin/acl/index/info")
    RestResult info();

}
