package com.atwzw.aclservice.service;

import com.alibaba.fastjson.JSONObject;
import com.atwzw.aclservice.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 权限服务类
 */
public interface SysPermissionService extends IService<SysPermission> {

    //获取全部菜单
    List<SysPermission> queryAllMenu();

    //根据角色获取菜单
    List<SysPermission> selectAllMenu(Long roleId);

    //给角色分配权限
    void saveRolePermissionRealtionShip(Long roleId, Long[] permissionId);

    //递归删除菜单
    void removeChildById(Long id);

    //根据用户id获取用户菜单
    List<String> selectPermissionValueByUserId(Long id);

    // 根据用户id获取用户菜单权限
    List<JSONObject> selectPermissionByUserId(Long id);

    //获取全部菜单
    List<SysPermission> queryAllMenuGuli();

    //递归删除菜单
    void removeChildByIdGuli(Long id);

    //给角色分配权限
    void saveRolePermissionRealtionShipGuli(Long roleId, Long[] permissionId);
}
