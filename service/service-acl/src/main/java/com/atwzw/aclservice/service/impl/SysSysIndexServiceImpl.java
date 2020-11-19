package com.atwzw.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atwzw.servicebase.bean.SysUser;
import com.atwzw.aclservice.entity.SysRole;
import com.atwzw.aclservice.service.SysUserService;
import com.atwzw.aclservice.service.SysIndexService;
import com.atwzw.aclservice.service.SysPermissionService;
import com.atwzw.aclservice.service.SysRoleService;
import com.atwzw.commonutils.ResultCodeEnum;
import com.atwzw.servicebase.exceptionhandler.BztException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 首页服务类实现
 */
@Service
public class SysSysIndexServiceImpl implements SysIndexService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据用户名获取用户登录信息
     *
     * @param username
     * @return
     */
    public Map<String, Object> getUserInfo(String username) {
        // 根据用户名获取对应的用户信息
        Map<String, Object> result = new HashMap<>();
        SysUser sysUser = sysUserService.selectByUsername(username);
        if (null == sysUser) {
            throw new BztException(ResultCodeEnum.CD502.getCode(),ResultCodeEnum.CD502.getMsg());
        }

        //根据用户id获取角色
        List<SysRole> sysRoleList = sysRoleService.selectRoleByUserId(sysUser.getId());
        List<String> roleNameList = sysRoleList.stream().map(item -> item.getRoleName()).collect(Collectors.toList());
        if (roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }

        //根据用户id获取操作权限值
        List<String> permissionValueList = sysPermissionService.selectPermissionValueByUserId(sysUser.getId());
        redisTemplate.opsForValue().set(username, permissionValueList);

        result.put("id", sysUser.getId());
        result.put("name", sysUser.getUsername());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }

    /**
     * 根据用户名获取动态菜单
     *
     * @param username
     * @return
     */
    public List<JSONObject> getMenu(String username) {
        // 根据用户名获取对应的用户信息
        SysUser sysUser = sysUserService.selectByUsername(username);

        // 根据用户id获取用户菜单权限
        List<JSONObject> permissionList = sysPermissionService.selectPermissionByUserId(sysUser.getId());
        return permissionList;
    }


}
