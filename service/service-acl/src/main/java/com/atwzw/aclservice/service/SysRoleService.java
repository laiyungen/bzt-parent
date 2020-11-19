package com.atwzw.aclservice.service;

import com.atwzw.aclservice.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 角色服务类
 */
public interface SysRoleService extends IService<SysRole> {

    //根据用户获取角色数据
    Map<String, Object> findRoleByUserId(Long userId);

    //根据用户分配角色
    void saveUserRoleRealtionShip(Long userId, Long[] roleId);

    // 根据用户 ID 获取其对应的所有角色
    List<SysRole> selectRoleByUserId(Long id);

    // 获取所有角色
    List<SysRole> selectRoleList();
}
