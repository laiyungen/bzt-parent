package com.atwzw.aclservice.internal;

import com.atwzw.aclservice.service.SysUserService;
import com.atwzw.commonutils.RestResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user/inner")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "根据父级用户 ID 查询出所有下级用户信息集合")
    @GetMapping("getAllChildUser/{userId}")
    public RestResult getAllChildrenSysUserListByPid(@ApiParam(name = "userId", value = "父级用户 ID", required = true) @PathVariable long userId) {
        List<Long> userIdList = sysUserService.getAllChildrenSysUserListByPid(userId, new ArrayList<>());
        return RestResult.success().data("userIdList", userIdList);
    }

}
