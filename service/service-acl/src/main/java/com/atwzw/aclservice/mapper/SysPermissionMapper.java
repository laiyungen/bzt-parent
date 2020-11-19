package com.atwzw.aclservice.mapper;

import com.atwzw.aclservice.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 权限 Mapper 接口
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    // 根据用户 ID 获取对应的权限值集合
    List<String> selectPermissionValueByUserId(Long id);

    // 获取全部菜单
    List<String> selectAllPermissionValue();

    // 根据用户 ID 获取对应的菜单权限集合
    List<SysPermission> selectPermissionByUserId(Long userId);
}
