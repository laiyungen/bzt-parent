package com.atwzw.aclservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.atwzw.aclservice.service.SysIndexService;
import com.atwzw.commonutils.RestResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description = "基本信息处理器")
@RestController
@RequestMapping("/admin/acl/index")
public class SysIndexController {

    @Autowired
    private SysIndexService sysIndexService;

    @ApiOperation(value = "根据 token 获取用户信息")
    @GetMapping("info")
    public RestResult info() {
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = sysIndexService.getUserInfo(username);
        return RestResult.success().data(userInfo);
    }

    @ApiOperation(value = "根据 token 获取菜单")
    @GetMapping("menu")
    public RestResult getMenu() {
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // 据用户名获取动态菜单
        List<JSONObject> permissionList = sysIndexService.getMenu(username);
        return RestResult.success().data("permissionList", permissionList);
    }

}
