package com.atwzw.amsservice.client;

import com.atwzw.amsservice.client.fuse.SysClientFuse;
import com.atwzw.commonutils.RestResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-acl", fallback = SysClientFuse.class)
@Component
public interface SysClient {

    @ApiOperation(value = "根据父级用户 ID 查询出所有下级用户信息集合")
    @GetMapping("/user/inner/getAllChildUser/{userId}")
    RestResult getAllChildrenSysUserListByPid(@ApiParam(name = "userId", value = "父级用户 ID", required = true) @PathVariable long userId);

}
